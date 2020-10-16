package ra.common.route;

import ra.common.network.NetworkPeer;

public interface ExternalRoute extends Route {
    NetworkPeer getOrigination();
    NetworkPeer getDestination();
    Boolean getSendContentOnly();
}
