package ra.common.notification;

import ra.common.content.JSON;
import ra.common.messaging.EventMessage;

import java.util.Map;

/**
 * Request a subscription to future publications providing an optional filter.
 */
public class SubscriptionRequest extends JSON {

    private EventMessage.Type type;
    private String filter;
    private Subscription subscription;

    public SubscriptionRequest(EventMessage.Type type, Subscription subscription) {
        this.type = type;
        this.subscription = subscription;
    }

    public SubscriptionRequest(EventMessage.Type type, String filter, Subscription subscription) {
        this.type = type;
        this.filter = filter;
        this.subscription = subscription;
    }

    public SubscriptionRequest(EventMessage.Type type, String service, String operation) {
        this.type = type;
        subscription = new ServiceSubscription(service, operation);
    }

    public SubscriptionRequest(EventMessage.Type type, String filter, String service, String operation) {
        this.type = type;
        this.filter = filter;
        subscription = new ServiceSubscription(service, operation);
    }

    public EventMessage.Type getEventMessageType() {
        return type;
    }

    public String getFilter() {
        return filter;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(type!=null) m.put("eventMessageType", type.name());
        if(filter!=null) m.put("filter", filter);
        if(subscription!=null && subscription instanceof ServiceSubscription) {
            m.put("subscription", ((ServiceSubscription)subscription).toMap());
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("eventMessageType") != null) type = EventMessage.Type.valueOf((String)m.get("eventMessageType"));
        if(m.get("filter") != null) filter = (String)m.get("filter");
        if(m.get("subscription")!=null) {
            subscription = new ServiceSubscription();
            ((ServiceSubscription)subscription).fromMap((Map<String,Object>)m.get("subscription"));

        }
    }
}
