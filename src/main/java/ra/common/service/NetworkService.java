package ra.common.service;

import ra.common.*;
import ra.common.messaging.Message;
import ra.common.messaging.MessageProducer;
import ra.common.network.*;
import ra.common.route.ExternalRoute;
import ra.common.route.Route;
import ra.util.tasks.TaskRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class NetworkService extends BaseService {

    private static Logger LOG = Logger.getLogger(NetworkService.class.getName());

    public static final String OPERATION_SEND = "SEND";
    public static final String OPERATION_REPLY = "REPLY";
    public static final String OPERATION_ADD_SEED = "ADD_SEED";
    public static final String OPERATION_NETWORK_STATE = "NETWORK_STATE";

    protected List<NetworkPeer> seeds = new ArrayList<>();
    protected NetworkState networkState;
    protected TaskRunner taskRunner;

    public NetworkService() {
        networkState = new NetworkState();
        networkState.network = this.getClass().getName();
        networkState.localPeer = new NetworkPeer(networkState.network);
    }

    public NetworkService(MessageProducer producer, ServiceStatusListener listener) {
        super(producer, listener);
        networkState = new NetworkState();
        networkState.network = this.getClass().getName();
        networkState.localPeer = new NetworkPeer(networkState.network);
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    @Override
    public void handleDocument(Envelope e) {
        // Incoming from internal Service requesting local Service
        Route r = e.getRoute();
        switch (r.getOperation()) {
            case OPERATION_SEND : {
                // A desire to send a packet to another Peer from an internal service
                Request request = null;
                if (r instanceof ExternalRoute) {
                    ExternalRoute exRoute = (ExternalRoute) r;
                    request = buildRequest(exRoute.getOrigination(), exRoute.getDestination());
                } else {
                    // Could be synchronous request for a resource (TOR/HTTPS)
                    request = new Request();
                }
                // Wrap Envelope with Request Packet
                request.setEnvelope(e);
                LOG.info("Sending Request...");
                if(!send(request)) {
                    Message m = e.getMessage();
                    if (m != null && m.getErrorMessages() != null && m.getErrorMessages().size() > 0) {
                        for (String err : m.getErrorMessages()) {
                            LOG.warning(err);
                            if ("BLOCKED".equals(err)) {
                                networkState.networkStatus = NetworkStatus.NETWORK_BLOCKED;
                            }
                        }
                    }
                }
                break;
            }
            case OPERATION_REPLY : {
                LOG.info("Replying with Envelope to requester...");
                Response response = (Response) DLC.getData(Response.class,e);
                if(response == null){
                    LOG.warning("Response required in envelope.");
                    return;
                }
                if (response.getDestinationPeer() == null) {
                    LOG.warning("Must provide a destination address when using a NetworkRequest.");
                    return;
                }
                if(!send(response)) {
                    Message m = e.getMessage();
                    if (m != null && m.getErrorMessages() != null && m.getErrorMessages().size() > 0) {
                        for (String err : m.getErrorMessages()) {
                            LOG.warning(err);
                            if ("BLOCKED".equals(err)) {
                                networkState.networkStatus = NetworkStatus.NETWORK_BLOCKED;
                            }
                        }
                    }
                }
                break;
            }
            case OPERATION_ADD_SEED: {
                NetworkPeer seed = (NetworkPeer)DLC.getEntity(e);
                seeds.add(seed);
                break;
            }
            case OPERATION_NETWORK_STATE: {
                DLC.addEntity(networkState, e);
                break;
            }
        }
    }

    protected abstract Request buildRequest(NetworkPeer origination, NetworkPeer destination);
    protected abstract boolean send(NetworkPacket networkPacket);

}
