package ca.ulaval.glo4003.evulution.infrastructure.email;

import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
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

    public void sendEmail(List<String> recipients, String subject, String message) throws EmailException {
        for (String recipientAddress : recipients) {
            try {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(this.email));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                throw new EmailException();
            }
        }
    }
}
