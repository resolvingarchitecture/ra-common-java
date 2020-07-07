package ra.common.service;

import ra.common.Envelope;
import ra.common.LifeCycle;
import ra.common.messaging.MessageConsumer;

/**
 * Service identifiable within Service Bus.
 */
public interface Service extends MessageConsumer, LifeCycle {
    String RA_SERVICE_IMPL = "ra.service.impl";
    void handleDocument(Envelope envelope);
    void handleEvent(Envelope envelope);
    void handleCommand(Envelope envelope);
    void handleHeaders(Envelope envelope);
    ServiceStatus getServiceStatus();
    ServiceReport report();
}
