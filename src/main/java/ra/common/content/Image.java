package ra.common.content;

/**
 * TODO: Add Description
 *
 */
public class Image extends Binary {

    public Image() {
    }

    public Image(byte[] body, String contentType) {
        super(body, contentType);
    }

    public Image(byte[] body, String contentType, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, name, generateHash, generateFingerprint);
    }
}
