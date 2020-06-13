package ra.common;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 */
public class Email implements Persistable, Serializable {

    public static String MIMETYPE_TEXT_PLAIN = "text/plain";

    private Long id;
    private String to;
    private String from;
    private String subject;
    private String message;
    private String messageType = MIMETYPE_TEXT_PLAIN;
    private int flag = 0;

    public Email() {
    }

    public Email(String to, String subject, String message) {
        // Anonymous Message
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Email(String to, String from, String subject, String message) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
