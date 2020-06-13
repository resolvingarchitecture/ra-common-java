package ra.common.route;

import ra.common.NetworkPeer;

public interface ExternalRoute extends Route {
    NetworkPeer getOrigination();
    NetworkPeer getDestination();
}
