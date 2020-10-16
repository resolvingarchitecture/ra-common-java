package ra.common.service;

import ra.common.network.NetworkAck;
import ra.common.route.RelayedExternalRoute;

public interface AcknowledgedNotification extends Operation {
    NetworkAck operate(RelayedExternalRoute packet);
}
