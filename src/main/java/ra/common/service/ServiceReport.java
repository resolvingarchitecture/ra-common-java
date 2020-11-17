package ra.common.service;

import ra.common.JSONSerializable;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceReport implements JSONSerializable {

    public String serviceClassName;
    public ServiceStatus serviceStatus;
    public Boolean registered = false;
    public Boolean running = false;
    public String version;
    public List<String> servicesDependentUpon;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(serviceClassName!=null) m.put("serviceClassName", serviceClassName);
        if(serviceStatus!=null) m.put("serviceStatus", serviceStatus.name());
        if(registered!=null) m.put("registered", registered);
        if(running!=null) m.put("running", running);
        if(version!=null) m.put("version", version);
        if(servicesDependentUpon!=null) m.put("servicesDependentUpon", servicesDependentUpon);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m!=null) {
            if(m.get("serviceClassName")!=null) serviceClassName = (String)m.get("serviceClassName");
            if(m.get("serviceStatus")!=null) serviceStatus = ServiceStatus.valueOf((String)m.get("serviceStatus"));
            if(m.get("registered")!=null) registered = Boolean.parseBoolean((String)m.get("registered"));
            if(m.get("running")!=null) running = Boolean.parseBoolean((String)m.get("running"));
            if(m.get("version")!=null) version = (String)m.get("version");
            if(m.get("servicesDependentUpon")!=null) servicesDependentUpon =  (List<String>)m.get("servicesDependentUpon");
        }
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
