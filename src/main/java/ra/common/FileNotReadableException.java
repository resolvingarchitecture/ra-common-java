package ra.common;

public class FileNotReadableException extends Exception {

    public FileNotReadableException() {
    }

    public FileNotReadableException(String message) {
        super(message);
    }
}
