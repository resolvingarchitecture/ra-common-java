package ra.common.notification;

import ra.common.content.JSON;

import java.util.Map;

public class ServiceSubscription extends JSON implements Subscription {

    private String service;
    private String operation;

    public ServiceSubscription(){}

    public ServiceSubscription(String service, String operation) {
        this.service = service;
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("service")!=null) service = (String)m.get("service");
        if(m.get("operation")!=null) operation = (String)m.get("operation");
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(service!=null) m.put("service", service);
        if(operation!=null) m.put("operation", operation);
        return m;
    }
}
