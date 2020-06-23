package ra.common.content;

/**
 * TODO: Add Description
 *
 */
public class Image extends Binary {

    public Image() {
    }

    public Image(byte[] body, String contentType, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, label, name, generateHash, generateFingerprint);
    }
}
