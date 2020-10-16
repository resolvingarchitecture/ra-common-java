package ra.common.service;

import ra.common.network.NetworkAck;
import ra.common.network.NetworkResponse;

public interface AcknowledgedHandleResponse extends Operation {
    NetworkAck operate(NetworkResponse networkResponse);
}
