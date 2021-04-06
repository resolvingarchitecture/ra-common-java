package ra.common.currency.wallet;

import ra.common.JSONSerializable;
import ra.common.currency.Coin;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public class Wallet implements JSONSerializable {

    protected String name;
    protected Coin amount;

    public Wallet(){}

    public Wallet(String name) {
        this.name = name;
    }

    public Wallet(String name, Coin amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coin getAmount() {
        return amount;
    }

    public void setAmount(Coin amount) {
        this.amount = amount;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("amount", amount);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        amount = (Coin)m.get("amount");
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
