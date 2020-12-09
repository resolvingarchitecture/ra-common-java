package ra.common.notification;

import ra.common.Client;
import ra.common.content.JSON;
import ra.common.messaging.EventMessage;

import java.util.Map;

/**
 * Request a subscription to future publications providing an optional filter.
 */
public class Subscription extends JSON {

    private EventMessage.Type type;
    private String filter;
    private String clientId;
    private volatile Client client;
    private String service;
    private String operation;

    public Subscription() {}

    public Subscription(EventMessage.Type type, Client client) {
        this.type = type;
        this.client = client;
    }

    public Subscription(EventMessage.Type type, String filter, Client client) {
        this.type = type;
        this.filter = filter;
        this.client = client;
    }

    public Subscription(EventMessage.Type type, String service, String operation) {
        this.type = type;
        this.service = service;
        this.operation = operation;
    }

    public Subscription(EventMessage.Type type, String filter, String service, String operation) {
        this.type = type;
        this.filter = filter;
        this.service = service;
        this.operation = operation;
    }

    public String getId() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());
        if(filter!=null) {
            sb.append("|");
            sb.append(filter);
        }
        sb.append("|");
        sb.append(service);
        sb.append("|");
        sb.append(operation);
        return sb.toString();
    }

    public EventMessage.Type getEventMessageType() {
        return type;
    }

    public void setEventMessageType(EventMessage.Type type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(type!=null) m.put("eventMessageType", type.name());
        if(filter!=null) m.put("filter", filter);
        if(clientId!=null) m.put("clientId", clientId);
        if(service!=null) m.put("service", service);
        if(operation!=null) m.put("operation", operation);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("eventMessageType") != null) type = EventMessage.Type.valueOf((String)m.get("eventMessageType"));
        if(m.get("filter") != null) filter = (String)m.get("filter");
        if(m.get("clientId") != null) clientId = (String)m.get("clientId");
        if(m.get("service")!=null) service = (String)m.get("service");
        if(m.get("operation")!=null) operation = (String)m.get("operation");
    }

}
