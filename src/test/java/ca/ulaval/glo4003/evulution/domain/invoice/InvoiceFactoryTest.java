package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class InvoiceFactoryTest {
    private static final int A_BANK_NO = 911;
    private static final int A_ACCOUNT_NO = 1999999;
    private static final String A_MONTHLY_FREQUENCY = "monthly";
    private static final String AN_INVALID_FREQUENCY = "invalid";
    private static final BigDecimal A_BALANCE = BigDecimal.valueOf(10000);

    private InvoiceFactory invoiceFactory;

    @BeforeEach
    void setUp() {
        this.invoiceFactory = new InvoiceFactory();
    }

    @Test
    public void givenInvalidFrequency_whenCreate_thenThrowsInvalidInvoiceException() {
        assertThrows(InvalidInvoiceException.class,
                () -> this.invoiceFactory.create(A_BANK_NO, A_ACCOUNT_NO, AN_INVALID_FREQUENCY, A_BALANCE));
    }

    @Test
    public void whenCreate_thenInvoiceIsCreated() {
        Invoice invoice = invoiceFactory.create(A_BANK_NO, A_ACCOUNT_NO, A_MONTHLY_FREQUENCY, A_BALANCE);

        assertNotNull(invoice);
    }
}
