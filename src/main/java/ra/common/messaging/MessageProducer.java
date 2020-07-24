package ra.common.messaging;

import ra.common.Client;
import ra.common.Envelope;

/**
 * Sends messages.
 */
public interface MessageProducer {
    boolean send(Envelope envelope);
    boolean send(Envelope envelope, Client callback);
}
