package ra.common.content;

/**
 * TODO: Add Description
 *
 */
public class JSON extends Text {

    public JSON() {
        super(null, "application/json");
    }

    public JSON(byte[] body) {
        super(body, "application/json");
    }

    public JSON(byte[] body, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, "application/json", label, name, generateHash, generateFingerprint);
    }
}
