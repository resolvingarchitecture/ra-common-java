package ra.common.identity;

import ra.common.JSONSerializable;
import ra.common.crypto.Addressable;
import ra.common.JSONParser;
import ra.common.JSONPretty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * TODO: Add Description
 *
 */
public class PublicKey implements Addressable, JSONSerializable {

    private String alias;
    private String fingerprint;
    private String address;
    private String type;
    private Boolean isIdentityKey = false;
    private Boolean isEncryptionKey = false;
    private Boolean isBase64Encoded = false;
    private Boolean isBase58Encoded = false;
    private Boolean isPEM = false;
    private Boolean isHex = false;
    // Name, Value
    private Map<String, Object> attributes = new HashMap<>();
    // Name, List of Signatures
    private Map<String, List<Signature>> signedAttributes = new HashMap<>();

    public PublicKey() {}

    public PublicKey(String address) {
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getFingerprint() {
        return fingerprint;
    }

    @Override
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIdentityKey() {
        return isIdentityKey;
    }

    public void isIdentityKey(boolean identityKey) {
        isIdentityKey = identityKey;
    }

    public boolean isEncryptionKey() {
        return isEncryptionKey;
    }

    public void isEncryptionKey(boolean encryptionKey) {
        isEncryptionKey = encryptionKey;
    }

    public Boolean isBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(Boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }

    public Boolean isBase58Encoded() {
        return isBase58Encoded;
    }

    public void setBase58Encoded(Boolean base58Encoded) {
        isBase58Encoded = base58Encoded;
    }

    public Boolean isPEM() {
        return isPEM;
    }

    public void setPEM(Boolean PEM) {
        isPEM = PEM;
    }

    public Boolean isHex() {
        return isHex;
    }

    public void setHex(Boolean hex) {
        isHex = hex;
    }

    public Map<String, Object> getAttributes(){
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String name, Object attribute) {
        if(attributes==null) {
            attributes = new HashMap<>();
        }
        attributes.put(name, attribute);
    }

    public Object getAttribute(String name) {
        if(attributes==null) {
            return null;
        }
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        if(attributes!=null) {
            attributes.remove(name);
        }
    }

    public Map<String, List<Signature>> getSignedAttributes() {
        return signedAttributes;
    }

    public void setSignedAttributes(Map<String, List<Signature>> signedAttributes) {
        this.signedAttributes = signedAttributes;
    }

    public void addSignedAttribute(String name, Signature signature) {
        if(isNull(signedAttributes))
            signedAttributes = new HashMap<>();
        if(isNull(signedAttributes.get(name)))
            signedAttributes.put(name, new ArrayList<>());
        signedAttributes.get(name).add(signature);
    }

    public void removeSignedAttribute(String name) {
        if(nonNull(signedAttributes) && signedAttributes.size() > 0) {
            signedAttributes.remove(name);
        }
    }

    public void removeSignature(String name, Signature signature) {
        if(signedAttributes!=null) {
            List<Signature> sigs = signedAttributes.get(name);
            if(sigs!=null) {
                Signature sigToRemove = null;
                for(Signature sig : sigs) {
                    if(sig.getSignedByAddress().equals(signature.getSignedByAddress())) {
                        sigToRemove = sig;
                        break;
                    }
                }
                if(nonNull(sigToRemove)) {
                    sigs.remove(sigToRemove);
                }
            }
        }
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(alias!=null) m.put("alias",alias);
        if(fingerprint!=null) m.put("fingerprint",fingerprint);
        if(address!=null) m.put("address", address);
        if(type!=null) m.put("type", type);
        if(isEncryptionKey!=null) m.put("isEncryptionKey",isEncryptionKey);
        if(isIdentityKey!=null) m.put("isIdentityKey",isIdentityKey);
        if(isBase64Encoded!=null) m.put("isBase64Encoded",isBase64Encoded.toString());
        if(isBase58Encoded!=null) m.put("isBase58Encoded",isBase58Encoded.toString());
        if(isPEM!=null) m.put("isPEM",isPEM);
        if(isHex!=null) m.put("isHex",isHex);
        if(attributes!=null && attributes.size() > 0) {
            m.put("attributes", attributes);
        }
        if(signedAttributes!=null && signedAttributes.size() > 0) {
            Map<String,List<Map<String,Object>>> mSigAtts = new HashMap<>();
            for(String att : signedAttributes.keySet()) {
                List<Map<String,Object>> sigMaps = new ArrayList<>();
                mSigAtts.put(att, sigMaps);
                List<Signature> sigs = signedAttributes.get(att);
                for(Signature sig : sigs) {
                    sigMaps.add(sig.toMap());
                }
            }
            m.put("signedAttributes", mSigAtts);
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("alias")!=null) alias = (String)m.get("alias");
        if(m.get("fingerprint")!=null) fingerprint = (String)m.get("fingerprint");
        if(m.get("address")!=null) address = (String)m.get("address");
        if(m.get("type")!=null) type = (String)m.get("type");
        if(m.get("isEncryptionKey")!=null) isEncryptionKey = (Boolean)m.get("isEncryptionKey");
        if(m.get("isIdentityKey")!=null) isIdentityKey = (Boolean)m.get("isIdentityKey");
        if(m.get("isBase64Encoded")!=null) isBase64Encoded = Boolean.parseBoolean((String)m.get("isBase64Encoded"));
        if(m.get("isBase58Encoded")!=null) isBase58Encoded = Boolean.parseBoolean((String)m.get("isBase58Encoded"));
        if(m.get("isPEM")!=null) isPEM = (Boolean)m.get("isPEM");
        if(m.get("isHex")!=null) isHex = (Boolean)m.get("isHex");
        if(m.get("attributes")!=null) attributes = (Map<String, Object>)m.get("attributes");
        if(m.get("signedAttributes")!=null) {
            signedAttributes = new HashMap<>();
            Map<String, List<Map<String,Object>>> mSigAtts = (Map<String, List<Map<String,Object>>>) m.get("signedAttributes");
            for(String att : mSigAtts.keySet()) {
                List<Signature> sigs = new ArrayList<>();
                signedAttributes.put(att, sigs);
                List<Map<String,Object>> sigMaps = mSigAtts.get(att);
                for(Map<String,Object> sigMap : sigMaps) {
                    Signature sig = new Signature();
                    sig.fromMap(sigMap);
                    sigs.add(sig);
                }
            }
        }
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
