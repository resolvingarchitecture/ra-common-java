package ra.common.notification;

import ra.common.Envelope;

/**
 * TODO: Add Description
 */
public interface Subscription {
    void notifyOfEvent(Envelope envelope);
}
