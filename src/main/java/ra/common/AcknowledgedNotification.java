package ra.common;

public interface AcknowledgedNotification extends Operation {
    Ack operate(NetworkPacket packet);
}
