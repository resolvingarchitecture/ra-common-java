package ra.common.service;

public interface Notification extends Operation {
    void notify(Packet packet);
}
