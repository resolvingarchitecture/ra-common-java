package ra.common.messaging;

import ra.common.Envelope;
import ra.common.LifeCycle;
import ra.common.service.ServiceLevel;

import java.util.Properties;

public interface MessageBus extends LifeCycle {
    void setConfig(Properties properties);
    MessageChannel registerChannel(String channelName);
    MessageChannel registerChannel(String channelName, ServiceLevel serviceLevel);
    MessageChannel registerChannel(String channelName, int maxSize, ServiceLevel serviceLevel, Class dataTypeFilter, boolean pubSub);
    MessageChannel registerSubscriberChannel(String channelName, String subscriberChannelName, int maxSize, ServiceLevel serviceLevel, Class dataTypeFilter, boolean pubSub);
    boolean registerAsynchConsumer(String channelName, MessageConsumer consumer);
    boolean publish(Envelope envelope);
    boolean clearUnprocessed();
    boolean resumeUnprocessed();
}
