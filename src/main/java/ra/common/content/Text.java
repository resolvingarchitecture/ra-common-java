package ra.common.content;

import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.Map;

public class Text extends Content {

    public Text() {
        contentType = "text/plain";
    }

    public Text(byte[] body){
        super.body = body;
        contentType = "text/plain";
    }

    protected Text(byte[] body, String contentType){
        super.body = body;
        super.contentType = contentType;
    }

    public Text(byte[] body, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, "text/plain", label, name, generateHash, generateFingerprint);
    }

    public Text(byte[] body, String contentType, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, label, name, generateHash, generateFingerprint);
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
