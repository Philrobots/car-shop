package ca.ulaval.glo4003.evulution.infrastructure.email;

import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EmailSenderImplTest {
    private static final String A_SENDER_EMAIL = "evulution.equipe6@gmail.com";
    private static final String A_PASSWORD = "architecture6";
    private static final String A_RECIPIENT_EMAIL = "yu-xuan.zhao.1@ulaval.ca";
    private static final String A_SUBJECT = "ILY";
    private static final String A_MESSAGE = "On t'aime youc";

    private EmailSenderImpl emailSenderImpl;

    private Email email;

    @BeforeEach
    void setUp() {
        this.emailSenderImpl = new EmailSenderImpl(A_SENDER_EMAIL, A_PASSWORD);
    }

    @Test
    void givenListOfOneValidEmail_whenSendEmail_thenDoesNotThrow() {
        // given
        List<String> recipients = List.of(A_RECIPIENT_EMAIL);

        // when
        Executable emailSenderExecution = () -> emailSenderImpl.sendEmail(recipients, A_SUBJECT, A_MESSAGE);

        // then
        assertDoesNotThrow(emailSenderExecution);
    }

    @Test
    void givenListOfMultipleValidEmail_whenSendEmail_thenDoesNotThrow() {
        // given
        List<String> recipients = List.of(A_RECIPIENT_EMAIL, A_RECIPIENT_EMAIL);

        // when
        Executable emailSenderExecution = () -> emailSenderImpl.sendEmail(recipients, A_SUBJECT, A_MESSAGE);

        // then
        assertDoesNotThrow(emailSenderExecution);
    }

    @Test
    void givenInvalidEmail_whenSendEmail_thenDoesThrow() {
        // given
        List<String> recipients = List.of("invalidEmail");

        // when
        Executable emailSenderExecution = () -> emailSenderImpl.sendEmail(recipients, A_SUBJECT, A_MESSAGE);

        // then
        assertThrows(EmailException.class, emailSenderExecution);
    }
}
