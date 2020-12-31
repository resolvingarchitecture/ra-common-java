package ra.common.currency.wallet;

import ra.common.JSONSerializable;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public class Wallet implements JSONSerializable {



    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();

        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {

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
