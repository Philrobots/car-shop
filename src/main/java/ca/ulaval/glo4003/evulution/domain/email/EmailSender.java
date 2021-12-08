package ca.ulaval.glo4003.evulution.domain.email;

import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

import java.util.List;

public interface EmailSender {
    public void sendEmail(List<String> recipients, String subject, String message) throws EmailException;
}
