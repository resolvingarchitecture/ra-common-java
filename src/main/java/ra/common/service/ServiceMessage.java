package ra.common.service;

import ra.common.JSONSerializable;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public abstract class ServiceMessage implements JSONSerializable {
    public static int NO_ERROR = -1;
    public static int REQUEST_REQUIRED = 0;

    public int statusCode = NO_ERROR;
    public String errorMessage;
    public Exception exception;
    public String type;

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("statusCode", statusCode +"");
        if(errorMessage!=null) m.put("errorMessage",errorMessage);
        if(exception!=null) m.put("exception",exception.getLocalizedMessage());
        m.put("type",getClass().getName());
        return m;
    }

    public void fromMap(Map<String, Object> m) {
        if(m.get("statusCode")!=null) this.statusCode = Integer.parseInt((String)m.get("statusCode"));
        if(m.get("errorMessage")!=null) this.errorMessage = (String)m.get("errorMessage");
        if(m.get("exception")!=null) {
            exception = new Exception((String)m.get("exception"));
        }
        if(m.get("type")!=null) type = (String)m.get("type");
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
