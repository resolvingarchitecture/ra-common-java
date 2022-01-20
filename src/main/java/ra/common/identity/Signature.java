package ra.common.identity;

import ra.common.JSONParser;
import ra.common.JSONPretty;
import ra.common.JSONSerializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class Signature implements JSONSerializable {

    private String valueSigned;
    private String algorithm;
    private Date signedDate;
    private String signedByUsername;
    private String signedByFingerprint;
    private String signedByAddress;

    public String getValueSigned() {
        return valueSigned;
    }

    public void setValueSigned(String valueSigned) {
        this.valueSigned = valueSigned;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public String getSignedByUsername() {
        return signedByUsername;
    }

    public void setSignedByUsername(String signedByUsername) {
        this.signedByUsername = signedByUsername;
    }

    public String getSignedByFingerprint() {
        return signedByFingerprint;
    }

    public void setSignedByFingerprint(String signedByFingerprint) {
        this.signedByFingerprint = signedByFingerprint;
    }

    public String getSignedByAddress() {
        return signedByAddress;
    }

    public void setSignedByAddress(String signedByAddress) {
        this.signedByAddress = signedByAddress;
    }

    @Override
    public int hashCode() {
        if(isNull(signedByAddress)) return 0;
        return signedByAddress.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(isNull(signedByAddress) || !(obj instanceof Signature) || isNull(((Signature)obj).signedByAddress)) return false;
        return ((Signature)obj).getSignedByAddress().equals(signedByAddress);
    }

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
        fromMap((Map<String, Object>)JSONParser.parse(json));
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
