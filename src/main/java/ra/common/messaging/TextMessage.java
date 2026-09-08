package ra.common.messaging;

import ra.common.identity.DID;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class TextMessage extends BaseMessage {

    protected DID to;
    protected DID from;
    protected String text;

    public TextMessage() {
    }

    public TextMessage(DID to, DID from, String text) {
        this.to = to;
        this.from = from;
        this.text = text;
    }

    public DID getTo() {
        return to;
    }

    public void setTo(DID to) {
        this.to = to;
    }

    public DID getFrom() {
        return from;
    }

    public void setFrom(DID from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(to!=null) m.put("to", to.toMap());
        if(from!=null) m.put("from", from.toMap());
        if(text!=null) m.put("text", text);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("to")!=null) {
            to = new DID();
            to.fromMap((Map<String, Object>)m.get("to"));
        }
        if(m.get("from")!=null) {
            from = new DID();
            from.fromMap((Map<String, Object>)m.get("from"));
        }
        if(m.get("text")!=null) text = (String)m.get("text");
    }
}
