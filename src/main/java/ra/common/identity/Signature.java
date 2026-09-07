package ra.common.identity;

import ra.common.JSONParser;
import ra.common.JSONPretty;
import ra.common.JSONSerializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * One signature over an attribute of a {@link PublicKey}.
 *
 * A {@code PublicKey} carries {@code Map<String, List<Signature>> signedAttributes}
 * — attribute name to the signatures asserting it — which is the persistence form
 * of the web-of-trust / {@code vouch} primitive: one identity signs an attribute
 * another identity claims.
 *
 * <p>{@code toMap()} / {@code fromMap()} were previously empty, so a
 * {@code Signature} could not round-trip through the InfoVault or JSON and any
 * vouch was silently lost on persistence. They now serialise every field.
 * {@code signedDate} is written as epoch milliseconds and read back tolerant of
 * either an {@code Integer} or a {@code Long} (the bundled JSON parser narrows
 * small numbers to {@code Integer}).
 */
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
        if(valueSigned != null) m.put("valueSigned", valueSigned);
        if(algorithm != null) m.put("algorithm", algorithm);
        if(signedDate != null) m.put("signedDate", signedDate.getTime());
        if(signedByUsername != null) m.put("signedByUsername", signedByUsername);
        if(signedByFingerprint != null) m.put("signedByFingerprint", signedByFingerprint);
        if(signedByAddress != null) m.put("signedByAddress", signedByAddress);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("valueSigned") != null) valueSigned = (String) m.get("valueSigned");
        if(m.get("algorithm") != null) algorithm = (String) m.get("algorithm");
        if(m.get("signedDate") != null) signedDate = new Date(((Number) m.get("signedDate")).longValue());
        if(m.get("signedByUsername") != null) signedByUsername = (String) m.get("signedByUsername");
        if(m.get("signedByFingerprint") != null) signedByFingerprint = (String) m.get("signedByFingerprint");
        if(m.get("signedByAddress") != null) signedByAddress = (String) m.get("signedByAddress");
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
