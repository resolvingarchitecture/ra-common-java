package ra.common.content;

public class HTML extends Text {

    public HTML() {contentType = "text/html";}

    public HTML(byte[] body, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, "text/html", label, name, generateHash, generateFingerprint);
    }
}
