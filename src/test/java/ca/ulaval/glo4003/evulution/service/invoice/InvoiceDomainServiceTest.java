package ca.ulaval.glo4003.evulution.service.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
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
public class InvoiceDomainServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleId saleId;

    @Mock
    private Invoice invoice;

    private InvoicePayment invoicePayment;

    @BeforeEach
    public void setUp() {
        invoicePayment = new InvoicePayment(saleRepository);
    }

    @Test
    public void givenActiveInvoice_whenMakePayments_thenSetSaleStatusToPaidIfNoPaymentsLeft()
            throws SaleNotFoundException {
        // given
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(0);
        this.invoicePayment.makeInvoiceActive(saleId, invoice);

        // when
        this.invoicePayment.makePayments();

        // then
        Mockito.verify(saleRepository).setStatus(saleId, SaleStatus.PAID);
    }
}
