package ca.ulaval.glo4003.evulution.domain.email;

import java.util.List;

public class Email {
    private final List<String> recipients;
    private final String subject;
    private final String message;

    public Email(List<String> recipients, String subject, String message) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
