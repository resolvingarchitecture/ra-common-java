package ra.common.network;

import ra.common.network.Response;

public class Ack extends Response {

    public Ack(String requestId) {
        super(requestId);
    }
}
