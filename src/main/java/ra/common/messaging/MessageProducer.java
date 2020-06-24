package ra.common.messaging;

import ra.common.Envelope;

/**
 * Sends messages.
 */
public interface MessageProducer {
    boolean send(Envelope envelope);
}
