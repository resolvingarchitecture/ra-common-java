package ra.common.currency;

import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCoin implements Coin {

    private Double value = 0.00;

    public BaseCoin() {}

    public BaseCoin(Double value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return toString();
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Double value() {
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
            if(m.get("value")!=null) value = (Double)m.get("value");
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
