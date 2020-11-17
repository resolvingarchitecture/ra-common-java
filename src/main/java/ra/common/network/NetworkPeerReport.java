package ra.common.network;

import java.util.Collection;

public class NetworkPeerReport {
    public String status;
    public Collection<NetworkPeer> seedPeers;
    public Collection<NetworkPeer> knownPeers;
    public Collection<NetworkPeer> activePeers;
}
