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
    private static final int PRODUCTION_TIME_IN_WEEKS = 2;

    @Mock
    private EmailSender emailSender;

    private EmailFactory emailFactory;

    @BeforeEach
    void setUp() {
        this.emailFactory = new EmailFactory(emailSender);
    }

    @Test
    public void whenCreateAssemblyDelayEmail_thenCreatesEmail() {
        // when
        Email email = this.emailFactory.createAssemblyDelayEmail(List.of(AN_EMAIL), A_DELIVERY_DATE);

        // then
        assertNotNull(email);
    }

    @Test
    public void whenCreateAssemblyFireBatteriesEmail_thenCreatesEmail() {
        // when
        Email email = this.emailFactory.createAssemblyFireBatteriesEmail(List.of(AN_EMAIL));

        // then
        assertNotNull(email);
    }

    @Test
    public void whenCreateBatteryInProductionEmail_thenCreatesEmail() {
        // when
        Email email = this.emailFactory.createBatteryInProductionEmail(List.of(AN_EMAIL), PRODUCTION_TIME_IN_WEEKS);

        // then
        assertNotNull(email);
    }

    @Test
    public void whenCreateVehicleInProductionEmail_thenCreatesEmail() {
        // when
        Email email = this.emailFactory.createVehicleInProductionEmail(List.of(AN_EMAIL), PRODUCTION_TIME_IN_WEEKS);

        // then
        assertNotNull(email);
    }

    @Test
    public void whenCreateAssemblyInProductionEmail_thenCreatesEmail() {
        // when
        Email email = this.emailFactory.createAssemblyInProductionEmail(List.of(AN_EMAIL), PRODUCTION_TIME_IN_WEEKS);

        // then
        assertNotNull(email);
    }
}
