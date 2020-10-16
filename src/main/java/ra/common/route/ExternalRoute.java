package ra.common.route;

import ra.common.network.NetworkPeer;

public interface ExternalRoute extends Route {

    int DESTINATION_PEER_REQUIRED = 2;
    int DESTINATION_PEER_WRONG_NETWORK = 3;
    int DESTINATION_PEER_NOT_FOUND = 4;
    int NO_SERVICE = 7;
    int NO_OPERATION = 8;
    int NO_ADDRESS = 9;
    int NO_FINGERPRINT = 10;
    int NO_PORT = 11;

    NetworkPeer getOrigination();
    NetworkPeer getDestination();
    Boolean getSendContentOnly();
}
