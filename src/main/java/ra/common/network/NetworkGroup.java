package ra.common.network;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkGroup {

    private final UUID id;
    private final Map<String,NetworkPeer> groupPeersById = new HashMap<>();
    private final Map<String,NetworkPeer> groupPeersByPubKeyFingerprint = new HashMap<>();
    private final Map<String,NetworkPeer> groupPeersByPubKeyAddress = new HashMap<>();

    public NetworkGroup(UUID id) {
        this.id = id;
    }

    public boolean addNetworkPeer(NetworkPeer np) {
        if(np==null || np.getId()==null || np.getDid()==null || np.getDid().getPublicKey()==null || np.getDid().getPublicKey().getFingerprint()==null || np.getDid().getPublicKey().getAddress()==null) return false;
        groupPeersById.put(np.getId(),np);
        groupPeersByPubKeyFingerprint.put(np.getDid().getPublicKey().getFingerprint(),np);
        groupPeersByPubKeyAddress.put(np.getDid().getPublicKey().getAddress(),np);
        return true;
    }

    public NetworkPeer getNetworkPeerById(String id) {
        return groupPeersById.get(id);
    }

    public NetworkPeer getNetworkPeerByFingerprint(String fingerprint) {
        return groupPeersByPubKeyFingerprint.get(fingerprint);
    }

    public NetworkPeer getNetworkPeerByAddress(String address) {
        return groupPeersByPubKeyAddress.get(address);
    }

}
