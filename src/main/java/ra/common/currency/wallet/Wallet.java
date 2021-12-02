package ra.common.currency.wallet;

import ra.common.JSONSerializable;
import ra.common.currency.Coin;
import ra.common.JSONParser;
import ra.common.JSONPretty;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public abstract class Wallet implements JSONSerializable {

    protected String name;
    protected Integer version;
    protected BigInteger balance;

    public Wallet(){}

    public Wallet(String name) {
        this.name = name;
    }

    public Wallet(String name, BigInteger balance) {
        this.name = name;
        this.balance = balance;
    }

    public Wallet(String name, Integer version, BigInteger balance) {
        this.name = name;
        this.version = version;
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

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("walletname", name);
        m.put("walletversion", version);
        m.put("balance", balance);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(nonNull(m.get("walletname"))) name = (String)m.get("walletname");
        if(nonNull(m.get("walletversion"))) version = (Integer)m.get("walletversion");
        if(nonNull(m.get("balance"))) balance = (BigInteger)m.get("balance");
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
