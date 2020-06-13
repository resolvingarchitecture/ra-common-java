package ra.common;

public interface AcknowledgedHandleResponse extends Operation {
    Ack operate(Response response);
}
