package ra.common.service;

import ra.common.service.Operation;
import ra.common.service.Packet;

public interface Notification extends Operation {
    void notify(Packet packet);
}
