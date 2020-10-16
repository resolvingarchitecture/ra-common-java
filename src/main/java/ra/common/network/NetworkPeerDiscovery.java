package ra.common.network;

import ra.common.DLC;
import ra.common.Envelope;

public class NetworkPeerDiscovery implements Runnable {

    private NetworkPeerManager peerManager;
    private NetworkBuilderStrategy strategy;
    boolean running = false;

    public NetworkPeerDiscovery(NetworkPeerManager peerManager, NetworkBuilderStrategy strategy) {
        this.peerManager = peerManager;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        running = true;
//        while(running) {
//
//        }
    }

    protected void send(String service, String operation, NetworkPeer destPeer) {
        Envelope e = Envelope.documentFactory();
        DLC.mark("netop", e);
        DLC.addExternalRoute(service, operation, e, peerManager.getLocalPeer(), destPeer);
        peerManager.send(e);
    }

    boolean receive(Envelope envelope) {

        return true;
    }
}
