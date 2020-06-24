package ra.common.service;

import ra.common.network.Ack;
import ra.common.network.NetworkPacket;

public interface AcknowledgedNotification extends Operation {
    Ack operate(NetworkPacket packet);
}
