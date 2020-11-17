package ra.common.network;

import ra.common.DLC;
import ra.common.Envelope;
import ra.common.Tuple2;
import ra.common.messaging.CommandMessage;
import ra.common.messaging.MessageProducer;
import ra.common.route.ExternalRoute;
import ra.common.service.BaseService;
import ra.common.service.ServiceStatusObserver;
import ra.util.RandomUtil;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class NetworkService extends BaseService {

    private static final Logger LOG = Logger.getLogger(NetworkService.class.getName());

    public static final String OPERATION_PEER_STATUS = "PEER_STATUS";
    public static final String OPERATION_PEER_STATUS_REPLY = "PEER_STATUS_REPLY";

    private final NetworkState networkState = new NetworkState();

    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<Tuple2<String,String>> stateChangeListeners = new ArrayList<>();

    protected Map<String,NetworkPeer> seedPeers = new HashMap<>();
    protected Map<String,NetworkPeer> knownPeers = new HashMap<>();
    protected Map<String,NetworkPeer> activePeers = new HashMap<>();

    protected Integer maxSeedPeers = 20;
    protected Integer maxKnownPeers = 500;
    protected Integer maxActivePeers = 100;

    protected NetworkService(String network) {
        this.networkState.network = network;
        this.networkState.localPeer = new NetworkPeer(network);
    }

    protected NetworkService(String network, MessageProducer producer, ServiceStatusObserver observer) {
        super(producer, observer);
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
                    report.activePeers = activePeers.values();
                    report.knownPeers = knownPeers.values();
                    report.seedPeers = seedPeers.values();
                    envelope.addContent(report);
                    break;
                }
                case OPERATION_PEER_STATUS_REPLY: {
                    NetworkPeerReport report = (NetworkPeerReport)envelope.getContent();
                    if("active".equals(report.status)) {
                        for(NetworkPeer remotePeer : report.activePeers){
                            if(activePeers.size() >= maxActivePeers) {
                                break;
                            }
                            activePeers.putIfAbsent(remotePeer.getId(), remotePeer);
                        }
                    }
                    break;
                }
            }
        }
    }

    public NetworkPeer lookupRemotePeer(String id) {
        if(activePeers.get(id)!=null)
            return activePeers.get(id);
        if(knownPeers.get(id)!=null)
            return knownPeers.get(id);
        if(seedPeers.get(id)!=null)
            return seedPeers.get(id);
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

    public Integer getMaxSeedPeers() {
        return maxSeedPeers;
    }

    public Integer getMaxKnownPeers() {
        return maxKnownPeers;
    }

    public Integer getMaxActivePeers() {
        return maxActivePeers;
    }

    public Integer getNumberKnownPeers() {
        return knownPeers.size();
    }

    public Collection<NetworkPeer> getKnownPeers() {
        return knownPeers.values();
    }

    public NetworkPeer getRandomKnownPeer() {
        if(knownPeers==null || knownPeers.size()==0) {
            return null;
        }
        if(knownPeers.size()==1) {
            return (NetworkPeer) new ArrayList(knownPeers.values()).get(1);
        }
        int randomPeer = RandomUtil.nextRandomInteger(0, knownPeers.size()-1);
        return (NetworkPeer) new ArrayList(knownPeers.values()).get(randomPeer);
    }

    public void addKnownPeer(NetworkPeer networkPeer) {
        if(seedPeers.get(networkPeer.getDid().getPublicKey().getFingerprint())==null && knownPeers.size() <= maxKnownPeers) {
            // Not a seed peer so add to known
            knownPeers.put(networkPeer.getDid().getPublicKey().getFingerprint(), networkPeer);
        }
    }

    public void addToKnownPeers(List<NetworkPeer> peers) {
        for(NetworkPeer np : peers) {
            if(seedPeers.get(np.getDid().getPublicKey().getFingerprint())==null && knownPeers.size() <= maxKnownPeers) {
                knownPeers.put(np.getDid().getPublicKey().getFingerprint(), np);
            }
        }
    }

    public void addSeedPeer(NetworkPeer networkPeer) {
        if(seedPeers.size() <= maxSeedPeers) {
            seedPeers.put(networkPeer.getDid().getPublicKey().getFingerprint(), networkPeer);
        }
    }

}
