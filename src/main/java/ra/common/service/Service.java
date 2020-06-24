package ra.common.service;

import ra.common.Envelope;

/**
 * Component within service bus for handling routing endpoints.
 *
 * @author objectorange
 */
public interface Service {
    void handleDocument(Envelope envelope);
    void handleEvent(Envelope envelope);
    void handleCommand(Envelope envelope);
    void handleHeaders(Envelope envelope);
    ServiceReport report();
}
