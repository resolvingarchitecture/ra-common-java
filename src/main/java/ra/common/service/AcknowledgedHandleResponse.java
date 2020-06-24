package ra.common.service;

import ra.common.network.Ack;
import ra.common.network.Response;

public interface AcknowledgedHandleResponse extends Operation {
    Ack operate(Response response);
}
