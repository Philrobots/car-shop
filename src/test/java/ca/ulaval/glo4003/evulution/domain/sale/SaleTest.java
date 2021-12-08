package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SaleTest {
    private static final Integer A_BANK_NUMBER = 200;
    private static final Integer AN_ACCOUNT_NUMBER = 1234567;
    private static final String A_FREQUENCY = "monthly";
    private static final SaleStatus SALE_STATUS_COMPLETED = SaleStatus.COMPLETED;

    @Mock
    private SaleId saleId;

    @Mock
    private AccountId accountId;

    @Mock
    private InvoiceFactory invoiceFactory;

    private Sale sale;

    @BeforeEach
    public void setUp() {
        this.sale = new Sale(accountId, saleId, invoiceFactory);
    }

    @Test
    public void givenSaleAlreadyCompleted_whenCompleteSale_thenThrowSaleCompleteException() {
        // given
        sale.setStatus(SALE_STATUS_COMPLETED);

        // when
        Executable completeSale = () -> this.sale.completeSale(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        // then
        assertThrows(SaleAlreadyCompleteException.class, completeSale);
    }

    @Test
    public void whenCompleteSale_thenInvoiceFactoryIsCalled()
            throws SaleAlreadyCompleteException, InvalidInvoiceException {
        // given
        this.sale.addPrice(BigDecimal.TEN);

        // when
        this.sale.completeSale(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        // then
        Mockito.verify(invoiceFactory).create(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY, BigDecimal.TEN);
    }
}
