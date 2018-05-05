package com.omerio.model;

import java.io.Serializable;

/**
 * @author omerio
 *
 */
public class EmailMessage implements Serializable {

    private static final long serialVersionUID = 7148626235918283023L;

    private String content;

    private String subject;

    private String fromEmail;

    private String toEmail;

    public EmailMessage() {
        super();
    }

    /**
     * Create a new email message
     * @param content - the body content
     * @param subject - the subject of the email
     * @param fromEmail - the sender
     * @param toEmail - the recipient
     */
    public EmailMessage(String content, String subject, String fromEmail, String toEmail) {
        super();
        this.content = content;
        this.subject = subject;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

}
