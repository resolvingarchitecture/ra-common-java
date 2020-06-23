package ra.common.content;

public class Video extends Binary {

    public Video() {}

    public Video(byte[] body, String contentType, String label, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, label, name, generateHash, generateFingerprint);
    }
}
