package ca.ulaval.glo4003.evulution.domain.email;

import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmailTest {
    private static final String AN_EMAIL = "johndoe@email.com";
    private static final String A_SUBJECT = "Welcome";
    private static final String A_MESSAGE = "Hello world";
    private static final List<String> RECIPIENTS = List.of(AN_EMAIL);

    @Mock
    private EmailSender emailSender;

    private Email email;

    @BeforeEach void setUp() {
        this.email = new Email(RECIPIENTS, A_SUBJECT, A_MESSAGE, emailSender);
    }

    @Test
    public void whenSend_thenEmailSenderSendsEmail() throws EmailException {
        // when
        this.email.send();

        // then
        Mockito.verify(emailSender).sendEmail(RECIPIENTS, A_SUBJECT, A_MESSAGE);
    }
}
