package ra.common.file;

public class FileNotReadableException extends Exception {

    public FileNotReadableException() {
    }

    public FileNotReadableException(String message) {
        super(message);
    }
}
