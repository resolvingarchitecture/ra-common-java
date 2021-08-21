package ra.common.currency.wallet;

import ra.common.JSONSerializable;
import ra.common.currency.Coin;
import ra.common.JSONParser;
import ra.common.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public class Wallet implements JSONSerializable {

    protected String name;
    protected Integer version;
    protected Coin balance;

    public Wallet(){}

    public Wallet(String name) {
        this.name = name;
    }

    public Wallet(String name, Coin balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Coin getBalance() {
        return balance;
    }

    public void setBalance(Coin balance) {
        this.balance = balance;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("balance", balance);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        balance = (Coin)m.get("balance");
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
