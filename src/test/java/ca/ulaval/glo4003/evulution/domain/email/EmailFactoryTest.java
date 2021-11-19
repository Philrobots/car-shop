package ca.ulaval.glo4003.evulution.domain.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailFactoryTest {
    private static final String AN_EMAIL = "email@email.com";
    private static final LocalDate A_DELIVERY_DATE = LocalDate.now();

    @Mock
    private EmailSender emailSender;

    private EmailFactory emailFactory;

    @BeforeEach
    void setUp() {
        this.emailFactory = new EmailFactory(emailSender);
    }

    @Test
    void whenCreateAssemblyDelayEmail_thenCreateEmail() {
        // when
        Email email = this.emailFactory.createAssemblyDelayEmail(List.of(AN_EMAIL), A_DELIVERY_DATE);

        // then
        assertNotNull(email);
    }
}
