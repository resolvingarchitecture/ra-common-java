package ra.common.network;

import ra.common.messaging.MessageProducer;
import ra.common.service.BaseService;
import ra.common.service.ServiceStatusListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NetworkService extends BaseService {

    private NetworkState networkState = new NetworkState();
    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<NetworkStateListener> stateChangeListeners = new ArrayList<>();

    public NetworkService() {
    }

    public NetworkService(MessageProducer producer, ServiceStatusListener listener) {
        super(producer, listener);
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
        networkState.networkStatus = networkStatus;
        for(NetworkStateListener l : stateChangeListeners) {
            l.stateChanged(networkState);
        }
    }

}
