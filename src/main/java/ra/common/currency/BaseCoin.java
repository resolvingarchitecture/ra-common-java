package ra.common.currency;

import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCoin implements Coin {

    private Long value = 0L;

    public BaseCoin() {}

    public BaseCoin(long value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return toString();
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public Long value() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toUpperCase();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(value!=null) m.put("value", value);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m!=null) {
            if(m.get("value")!=null) value = (Long)m.get("value");
        }
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
    }

    @Override
    public void fromJSON(String json) {
        fromMap((Map<String,Object>)JSONParser.parse(json));
    }
}
