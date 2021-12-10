package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvoicePaymentTest {
    private static final int ZERO_PAYMENT_LEFT = 0;
    private static final int ONE_PAYMENT_LEFT = 1;
    private static final SaleStatus PAID_STATUS = SaleStatus.PAID;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleId saleId;

    @Mock
    private Invoice invoice;

    private InvoicePayment invoicePayment;

    @BeforeEach
    void setUp() {
        this.invoicePayment = new InvoicePayment(saleRepository);
    }

    @Test
    public void givenInvoice_whenMakePayments_thenSaleRepositorySetsStatus() throws SaleNotFoundException {
        // given
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(ZERO_PAYMENT_LEFT);
        this.invoicePayment.makeInvoiceActive(saleId, invoice);

        // when
        this.invoicePayment.makePayments();

        // then
        Mockito.verify(saleRepository).setStatus(saleId, PAID_STATUS);
    }

    @Test
    public void givenInvoice_whenMakePayments_thenMakeAPayment() throws SaleNotFoundException {
        // given
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(ONE_PAYMENT_LEFT);
        this.invoicePayment.makeInvoiceActive(saleId, invoice);

        // when
        this.invoicePayment.makePayments();

        // then
        Mockito.verify(invoice).pay();
    }
}
