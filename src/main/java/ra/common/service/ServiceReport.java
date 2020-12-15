package ra.common.service;

import ra.common.content.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceReport extends JSON {

    public String serviceClassName;
    public ServiceStatus serviceStatus;
    public Boolean registered = false;
    public Boolean running = false;
    public String version;
    public List<String> servicesDependentUpon;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
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
        super.fromMap(m);
        if(m.get("serviceClassName")!=null) serviceClassName = (String)m.get("serviceClassName");
        if(m.get("serviceStatus")!=null) serviceStatus = ServiceStatus.valueOf((String)m.get("serviceStatus"));
        if(m.get("registered")!=null) registered = (Boolean)m.get("registered");
        if(m.get("running")!=null) running =(Boolean)m.get("running");
        if(m.get("version")!=null) version = (String)m.get("version");
        if(m.get("servicesDependentUpon")!=null) servicesDependentUpon =  (List<String>)m.get("servicesDependentUpon");
    }

}
