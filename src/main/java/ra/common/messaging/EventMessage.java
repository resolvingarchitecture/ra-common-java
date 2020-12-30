package ra.common.messaging;

import ra.common.JSONSerializable;
import ra.common.content.JSON;
import ra.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Events
 *
 */
public final class EventMessage extends BaseMessage {

    private static final Logger LOG = Logger.getLogger(EventMessage.class.getName());

    public enum Type {
        ERROR,
        EXCEPTION,
        BUS_STATUS,
        PEER_STATUS,
        SERVICE_STATUS,
        DID_STATUS,
        NETWORK_STATE_UPDATE,
        PRICE_CHANGE
    }

    private String id = UUID.randomUUID().toString();

    private String type;
    private String name;
    private JSONSerializable message;

    public EventMessage() {}

    public EventMessage(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setMessage(JSONSerializable message) {
        this.message = message;
    }

    public JSONSerializable getMessage() {
        return message;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        m.put("type", type);
        if(name!=null) m.put("name", name);
        if(message!=null) m.put("message", message.toMap());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        type = (String)m.get("type");
        name = (String)m.get("name");
        Map<String,Object> mObj = (Map<String,Object>)m.get("message");
        Object typeObj = mObj.get("type");
        if(typeObj instanceof String) {
            String type = (String)typeObj;
            try {
                Object objMsg = Class.forName(type).getConstructor().newInstance();
                if(objMsg instanceof JSONSerializable) {
                    message = (JSONSerializable) objMsg;
                    message.fromMap(mObj);
                }
            } catch (Exception e) {
                LOG.warning(e.getLocalizedMessage());
            }
        }
    }
}
