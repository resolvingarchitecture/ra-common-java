package ra.common.network;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the Network. It represents the local physical
 * machine on a network and the local networks supported.
 */
public final class NetworkNode {

    private Map<String, NetworkState> networks = new HashMap<>();

    public NetworkNode() {}

    public void addNetwork(NetworkState networkState) {
        networks.put(networkState.network.name(), networkState);
    }

    public NetworkPeer getNetworkPeer(Network network) {
        if(networks.get(network.name())==null) {
            return null;
        } else {
            return networks.get(network.name()).localPeer;
        }
    }

    public void removeNetwork(String networkName) {
        networks.remove(networkName);
    }

    public int numberOfNetworks() {
        return networks.size();
    }

    @Override
    public String toString() {
        String json = "{networks:[";
        boolean first = true;
        for(NetworkState ns : networks.values()) {
            if(!first)
                json+=", ";
            json+=ns.toJSON();
            first = false;
        }
        return json+"]}";
    }
}
