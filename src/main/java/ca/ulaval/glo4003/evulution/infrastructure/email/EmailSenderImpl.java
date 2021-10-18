package ca.ulaval.glo4003.evulution.infrastructure.email;

import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderImpl implements EmailSender {
    private final String email;
    private final Session session;

    public EmailSenderImpl(String email, String password) {
        this.email = email;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public void sendEmail(Email email) {
        for (String recipientAddress : email.getRecipients()) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(this.email));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));
                message.setSubject(email.getSubject());
                message.setText(email.getMessage());

                Transport.send(message);
            } catch (MessagingException e) {
                throw new EmailException();
            }
        }
    }
}
