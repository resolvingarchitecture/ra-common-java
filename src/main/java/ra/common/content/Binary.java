package ra.common.content;

public abstract class Binary extends Content {

    public Binary() {
    }

    public Binary(byte[] body, String contentType, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, label, name, generateHash, generateFingerprint);
    }
}
