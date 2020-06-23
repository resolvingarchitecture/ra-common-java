package ra.common.content;

public class Audio extends Binary {

    public Audio() {}

    public Audio(byte[] body, String contentType, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, label, name, generateHash, generateFingerprint);
    }
}
