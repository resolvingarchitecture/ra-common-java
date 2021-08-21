package ra.common.currency;

import ra.common.JSONParser;
import ra.common.JSONPretty;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseCoin implements Coin {

    private BigInteger value;

    public BaseCoin() {}

    public BaseCoin(BigInteger value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return toString();
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public BigInteger value() {
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
            if(m.get("value")!=null) value = (BigInteger)m.get("value");
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
