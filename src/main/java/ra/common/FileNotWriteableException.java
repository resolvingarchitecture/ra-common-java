package ra.common;

public class FileNotWriteableException extends Exception {

    public FileNotWriteableException() {
    }

    public FileNotWriteableException(String message) {
        super(message);
    }
}
