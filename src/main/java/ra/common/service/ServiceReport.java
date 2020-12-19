package ra.common.service;

import ra.common.JSONSerializable;
import ra.common.content.JSON;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceReport implements JSONSerializable {

    public String type = this.getClass().getName();
    public String serviceClassName;
    public ServiceStatus serviceStatus;
    public Boolean registered = false;
    public Boolean running = false;
    public String version;
    public List<String> servicesDependentUpon;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("type", type);
        m.put("serviceClassName", serviceClassName);
        m.put("serviceStatus", serviceStatus.name());
        m.put("registered", registered);
        m.put("running", running);
        m.put("version", version);
        m.put("servicesDependentUpon", servicesDependentUpon);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        type = (String)m.get("type");
        serviceClassName = (String)m.get("serviceClassName");
        serviceStatus = ServiceStatus.valueOf((String)m.get("serviceStatus"));
        registered = (Boolean)m.get("registered");
        running =(Boolean)m.get("running");
        version = (String)m.get("version");
        servicesDependentUpon =  (List<String>)m.get("servicesDependentUpon");
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
    }

    @Override
    public void fromJSON(String json) {
        fromMap((Map<String, Object>)JSONParser.parse(json));
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
