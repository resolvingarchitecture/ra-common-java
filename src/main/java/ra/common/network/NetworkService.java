package ra.common.network;

import ra.common.DLC;
import ra.common.Envelope;
import ra.common.Tuple2;
import ra.common.messaging.MessageProducer;
import ra.common.service.BaseService;
import ra.common.service.ServiceStatusListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class NetworkService extends BaseService {

    private static final Logger LOG = Logger.getLogger(NetworkService.class.getName());

    private final NetworkState networkState = new NetworkState();

    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<Tuple2<String,String>> stateChangeListeners = new ArrayList<>();

    protected Map<String,NetworkPeer> knownPeers = new HashMap<>();
    protected Map<String,NetworkPeer> activePeers = new HashMap<>();

    protected NetworkService(String network, MessageProducer producer, ServiceStatusListener listener) {
        super(producer, listener);
        this.networkState.network = network;
        this.networkState.localPeer = new NetworkPeer(network);
    }

    public void registerStatusListener(Tuple2<String,String> listener) {
        stateChangeListeners.add(listener);
    }

    public void unregisterStatusListener(Tuple2<String,String> listener) {
        stateChangeListeners.remove(listener);
    }

    public void registerSessionListener(NetworkClientSessionListener listener) {
        sessionListeners.add(listener);
    }

    public void unregisterSessionListener(NetworkClientSessionListener listener) {
        sessionListeners.remove(listener);
    }

    protected void updateNetworkStatus(NetworkStatus networkStatus) {
        LOG.info("Network Status for Network: "+networkState.network +" - " + networkStatus.name());
        networkState.networkStatus = networkStatus;
        for(Tuple2<String,String> l : stateChangeListeners) {
            // Send to Service, Operation
            Envelope e = Envelope.documentFactory();
            DLC.addContent(networkStatus, e);
            DLC.addRoute(l.first, l.second, e);
            send(e);
        }
    }

    protected void connectionReport(NetworkConnectionReport report) {
        LOG.info("Network Connection Report for Network: "+networkState.network+"\n\t"+report.toJSON());
        networkState.connectionReports.add(report);
        for(Tuple2<String,String> l : stateChangeListeners) {
            // Send to Service, Operation
            Envelope e = Envelope.documentFactory();
            DLC.addContent(report, e);
            DLC.addRoute(l.first, l.second, e);
            send(e);
        }
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public abstract Boolean sendOut(Envelope envelope);

    protected Boolean receiveIn(Envelope envelope) {
        return false;
    }

}
