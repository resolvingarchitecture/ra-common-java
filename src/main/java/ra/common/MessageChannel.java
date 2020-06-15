package ra.common;

import java.util.List;

public interface MessageChannel extends MessageProducer, LifeCycle {
    void registerAsyncConsumer(MessageConsumer consumer);
    void registerSubscriptionChannel(MessageChannel channel);
    int queued();
    String getName();
    boolean getPubSub();
    List<MessageChannel> getSubscriptionChannels();
    boolean send(Envelope e);
    Envelope receive();
    Envelope receive(int timeout);
    Envelope poll();
    void ack(Envelope envelope);
    void setFlush(boolean flush);
    boolean getFlush();
    boolean clearUnprocessed();
    boolean sendUnprocessed();
}
