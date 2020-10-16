package ra.common.network;

import ra.common.service.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NetworkService extends BaseService {

    protected NetworkState networkState = new NetworkState();
    protected Map<String,NetworkClientSession> sessions = new HashMap<>();
    protected List<NetworkClientSessionListener> sessionListeners = new ArrayList<>();
    protected List<NetworkStateListener> statusListeners = new ArrayList<>();

    public void registerStatusListener(NetworkStateListener listener) {
        statusListeners.add(listener);
    }

    public void unregisterStatusListener(NetworkStateListener listener) {
        statusListeners.remove(listener);
    }

    public void registerSessionListener(NetworkClientSessionListener listener) {
        sessionListeners.add(listener);
    }

    public void unregisterSessionListener(NetworkClientSessionListener listener) {
        sessionListeners.remove(listener);
    }

}
