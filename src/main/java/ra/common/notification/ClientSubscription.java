package ra.common.notification;

import ra.common.Envelope;

public interface ClientSubscription extends Subscription {

    void reply(Envelope envelope);

}
