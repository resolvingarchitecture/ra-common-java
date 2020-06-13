package ra.common;

public interface Notification extends Operation {
    void notify(Packet packet);
}
