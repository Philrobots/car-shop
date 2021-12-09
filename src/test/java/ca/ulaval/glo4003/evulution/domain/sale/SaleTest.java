package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SaleTest {

    private static final Integer A_BANK_NUMBER = 200;
    private static final Integer AN_ACCOUNT_NUMBER = 1234567;

    private static final String A_FREQUENCY = "monthly";

    private static final SaleStatus SALE_STATUS_COMPLETED = SaleStatus.COMPLETED;

    private static final AccountId A_VALID_ACCOUNT_ID = new AccountId();
    private static final AccountId AN_INVALID_ACCOUNT_ID = new AccountId();

    @Mock
    private SaleId saleId;

    @Mock
    private Invoice invoice;

    @Mock
    private InvoiceFactory invoiceFactory;

    @Mock
    private InvoicePayment invoicePayment;

    private Sale sale;

    @BeforeEach
    public void setUp() {
        this.sale = new Sale(A_VALID_ACCOUNT_ID, saleId, invoiceFactory);
    }

    @Test
    public void givenValidAccountId_whenVerifyAccountId_thenAssertsTrue() {
        // when
        Executable executable = () -> sale.verifyAccountId(A_VALID_ACCOUNT_ID);
        // then
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    public void givenInvalidAccountId_whenVerifyAccountId_thenThrowsMismatchAccountIdWithSaleException() {
        // when
        Executable executable = () -> sale.verifyAccountId(AN_INVALID_ACCOUNT_ID);

        // then
        Assertions.assertThrows(MismatchAccountIdWithSaleException.class, executable);
    }

    @Test
    public void whenCompleteSale_thenInvoiceFactoryCreates()
            throws SaleAlreadyCompleteException, InvalidInvoiceException {
        // when
        sale.completeSale(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY);

        // then
        Mockito.verify(invoiceFactory).create(A_BANK_NUMBER, AN_ACCOUNT_NUMBER, A_FREQUENCY, BigDecimal.ZERO);
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
    public void givenIncompleteStatus_whenVerifyIsComplete_thenThrowIncompleteSaleException() {
        // when
        Executable verifyIsComplete = () -> this.sale.verifyIsCompleted();

        // then
        assertThrows(IncompleteSaleException.class, verifyIsComplete);
    }

    @Test
    public void givenCompleteStatus_whenVerifyIsComplete_thenShouldNotThrow() {
        // given
        sale.setStatus(SALE_STATUS_COMPLETED);

        // when
        Executable verifyIsComplete = () -> this.sale.verifyIsCompleted();

        // then
        assertDoesNotThrow(verifyIsComplete);
    }

    @Test
    public void givenIncompleteStatus_whenVerifyNotComplete_thenShouldNotThrow() {
        // when
        Executable verifyNotComplete = () -> this.sale.verifyNotCompleted();

        // then
        assertDoesNotThrow(verifyNotComplete);
    }

    @Test
    public void givenCompleteStatus_whenVerifyNotComplete_thenThrowSaleAlreadyCompleteException() {
        // given
        sale.setStatus(SALE_STATUS_COMPLETED);

        // when
        Executable verifyIsComplete = () -> this.sale.verifyIsCompleted();

        // then
        assertDoesNotThrow(verifyIsComplete);
    }
}
