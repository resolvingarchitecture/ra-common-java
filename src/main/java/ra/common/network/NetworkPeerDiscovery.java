package ra.common.network;

import ra.common.Envelope;

import java.util.List;

public class NetworkPeerDiscovery implements Runnable {

    private NetworkService service;
    private NetworkBuilderStrategy strategy;
    private List<NetworkPeer> seeds;
    boolean running = false;

    public NetworkPeerDiscovery(NetworkService service, NetworkBuilderStrategy strategy, List<NetworkPeer> seeds) {
        this.service = service;
        this.strategy = strategy;
        this.seeds = seeds;
    }

    @Override
    public void run() {
        running = true;
//        while(running) {
//
//        }
    }

    protected void send(String service, String operation, NetworkPeer destPeer) {

    }

    boolean receive(Envelope envelope) {

        return true;
    }
}
