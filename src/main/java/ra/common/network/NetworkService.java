package ra.common.network;

import ra.common.DLC;
import ra.common.Envelope;
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
    protected final NetworkPeerManager peerManager;

    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<NetworkStateListener> stateChangeListeners = new ArrayList<>();

    public NetworkService(MessageProducer producer, ServiceStatusListener listener, NetworkBuilderStrategy strategy) {
        super(producer, listener);
        this.peerManager = new NetworkPeerManager(this, networkState, strategy);
    }

    public void registerStatusListener(NetworkStateListener listener) {
        stateChangeListeners.add(listener);
    }

    public void unregisterStatusListener(NetworkStateListener listener) {
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
        for(NetworkStateListener l : stateChangeListeners) {
            l.stateChanged(networkState);
        }
    }

    protected void connectionReport(NetworkConnectionReport report) {
        LOG.info("Network Connection Report for Network: "+networkState.network+"\n\t"+report.toJSON());
        networkState.connectionReports.add(report);
        for(NetworkStateListener l : stateChangeListeners) {
            l.stateChanged(networkState);
        }
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public abstract Boolean sendOut(Envelope envelope);

    public Boolean receiveIn(Envelope envelope) {
        if(DLC.markerPresent("netop", envelope))
            peerManager.receive(envelope);
        return true;
    }

}
