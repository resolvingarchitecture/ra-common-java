package ra.common.network;

import ra.common.Envelope;
import ra.common.Tuple2;
import ra.common.messaging.CommandMessage;
import ra.common.messaging.EventMessage;
import ra.common.messaging.MessageProducer;
import ra.common.route.ExternalRoute;
import ra.common.service.BaseService;
import ra.common.service.ServiceStatusObserver;
import ra.common.RandomUtil;

import java.util.*;
import java.util.logging.Logger;

public abstract class NetworkService extends BaseService {

    private static final Logger LOG = Logger.getLogger(NetworkService.class.getName());

    public static final String OPERATION_PEER_STATUS = "PEER_STATUS";
    public static final String OPERATION_PEER_STATUS_REPLY = "PEER_STATUS_REPLY";

    private final NetworkState networkState = new NetworkState();

    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<Tuple2<String,String>> stateChangeListeners = new ArrayList<>();

    protected Map<String,NetworkPeer> peers = new HashMap<>();
    protected NetworkPeer localPeer;

    protected Integer maxPeers = 500;

    protected NetworkService() {
        super();
    }

    protected NetworkService(Network network) {
        this.networkState.network = network;
        this.networkState.localPeer = new NetworkPeer(network);
    }

    protected NetworkService(Network network, MessageProducer producer, ServiceStatusObserver observer) {
        super(producer, observer);
        this.networkState.network = network;
        this.networkState.localPeer = new NetworkPeer(network);
    }

    public void setNetwork(Network network) {
        this.networkState.network = network;
        this.networkState.localPeer = new NetworkPeer(network);
    }

    @Override
    public void handleCommand(Envelope envelope) {
        super.handleCommand(envelope);
        CommandMessage msg = (CommandMessage)envelope.getMessage();
        switch(msg.getCommand()) {
            case NetState: {
                envelope.addNVP("result", networkState);
                break;
            }
            case RegisterStateChangeListener: {
                if(envelope.getValue("listener")!=null) {
                    Tuple2<String,String> listener = (Tuple2<String, String>)envelope.getValue("listener");
                    registerStateChangeListener(listener);
                }
                break;
            }
            case UnregisterStateChangeListener: {
                if(envelope.getValue("listener")!=null) {
                    Tuple2<String,String> listener = (Tuple2<String, String>)envelope.getValue("listener");
                    unregisterStateChangeListener(listener);
                }
                break;
            }
        }
    }

    @Override
    public void handleDocument(Envelope envelope) {
        if(envelope.getRoute() instanceof ExternalRoute) {
            ExternalRoute er = (ExternalRoute)envelope.getRoute();
            switch(er.getOperation()) {
                case OPERATION_PEER_STATUS: {
                    // A request from a remote peer for this peer's status
                    NetworkPeerReport report = new NetworkPeerReport();
                    report.status = "active";
                    report.peers = peers.values();
                    envelope.addContent(report);
                    break;
                }
                case OPERATION_PEER_STATUS_REPLY: {
                    NetworkPeerReport report = (NetworkPeerReport)envelope.getContent();
                    if("active".equals(report.status)) {
                        for(NetworkPeer remotePeer : report.peers){
                            if(peers.size() >= maxPeers) {
                                break;
                            }
                            peers.putIfAbsent(remotePeer.getId(), remotePeer);
                        }
                    }
                    break;
                }
            }
            // Send info of from peer to network manager
            Envelope e = Envelope.documentFactory();
            e.addRoute("ra.networkmanager.NetworkManagerService","UPDATE_PEER");
            e.addNVP(NetworkPeer.class.getName(), er.getOrigination());
            send(e);
        }
    }

    public NetworkPeer lookupRemotePeer(String id) {
        if(peers.get(id)!=null)
            return peers.get(id);
        else
            return null;
    }

    public void registerStateChangeListener(Tuple2<String,String> listener) {
        stateChangeListeners.add(listener);
    }

    public void unregisterStateChangeListener(Tuple2<String,String> listener) {
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
        Envelope e = Envelope.eventFactory(EventMessage.Type.NETWORK_STATE_UPDATE);
        EventMessage em = (EventMessage)e.getMessage();
        em.setMessage(networkState);
        e.addRoute("ra.networkmanager.NetworkManagerService", "UPDATE_NETWORK_STATE");
        send(e);
    }

    protected void connectionReport(NetworkConnectionReport report) {
        LOG.info("Network Connection Report for Network: "+networkState.network+"\n\t"+report.toJSON());
        networkState.connectionReports.add(report);
        for(Tuple2<String,String> l : stateChangeListeners) {
            // Send to Service, Operation
            Envelope e = Envelope.documentFactory();
            e.addContent(report);
            e.addRoute(l.first, l.second);
            send(e);
        }
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    protected abstract Boolean sendOut(Envelope envelope);

    public Integer getMaxPeers() {
        return maxPeers;
    }

    public NetworkPeer getLocalPeer() {
        return localPeer;
    }

    public Integer getNumberPeers() {
        return peers.size();
    }

    public Collection<NetworkPeer> getPeers() {
        return peers.values();
    }

    public NetworkPeer getRandomPeer() {
        if(peers==null || peers.size()==0) {
            return null;
        }
        if(peers.size()==1) {
            return (NetworkPeer) new ArrayList(peers.values()).get(0);
        }
        int randomPeer = RandomUtil.nextRandomInteger(0, peers.size()-1);
        return (NetworkPeer) new ArrayList(peers.values()).get(randomPeer);
    }

    public void addPeers(Collection<NetworkPeer> peers) {
        for(NetworkPeer np : peers) {
            addPeer(np);
        }
    }

    public void addPeer(NetworkPeer networkPeer) {
        if(peers.get(networkPeer.getDid().getPublicKey().getFingerprint())==null && peers.size() <= maxPeers) {
             peers.put(networkPeer.getDid().getPublicKey().getFingerprint(), networkPeer);
        }
    }

}
