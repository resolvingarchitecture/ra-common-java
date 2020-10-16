package ra.common.network;

import ra.common.Envelope;

import java.util.HashMap;
import java.util.Map;

public class NetworkPeerManager implements NetworkStateListener {

    private NetworkService service;
    private NetworkState networkState;
    private Boolean networkDiscovererable;
    private NetworkPeerDiscovery peerDiscovery;
    private Map<String, NetworkPeer> remotePeers = new HashMap<>();

    public NetworkPeerManager(NetworkService service, NetworkState networkState, NetworkBuilderStrategy strategy) {
        this.service = service;
        this.networkState = networkState;
        this.networkDiscovererable = strategy!=null;
        if(networkDiscovererable) peerDiscovery = new NetworkPeerDiscovery(this, strategy);
    }

    @Override
    public void stateChanged(NetworkState latestState) {
        if(latestState.networkStatus == NetworkStatus.CONNECTED
                && networkDiscovererable
                && !peerDiscovery.running) {
            new Thread(peerDiscovery).start();
        }
    }

    NetworkPeer getLocalPeer() {
        return networkState.localPeer;
    }

    void send(Envelope e) {
        service.sendOut(e);
    }

    void receive(Envelope e) {
        peerDiscovery.receive(e);
    }

//    NetworkPeer randomPeer() {
//        if(remotePeers.size() > 0) {
//
//        }
//    }

}
