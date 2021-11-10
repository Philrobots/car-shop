package ca.ulaval.glo4003.evulution.service.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private TransactionId transactionId;

    @Mock
    private Invoice invoice;

    private InvoiceService invoiceService;

    @BeforeEach
    public void setUp() {
        invoiceService = new InvoiceService(invoiceRepository, saleRepository);
    }

    @Test
    public void whenMakeInvoiceActive_thenInvoiceRepositoryGetsInvoiceFromTransactionId() {
        BDDMockito.given(invoiceRepository.getInvoiceFromTransactionId(transactionId)).willReturn(invoice);

        // when
        invoiceService.makeInvoiceActive(transactionId);

        // then
        Mockito.verify(invoiceRepository).getInvoiceFromTransactionId(transactionId);
    }

    @Test
    public void whenMakeInvoiceActive_thenInvoiceMakesPayment() {
        BDDMockito.given(invoiceRepository.getInvoiceFromTransactionId(transactionId)).willReturn(invoice);

        // when
        invoiceService.makeInvoiceActive(transactionId);

        // then
        Mockito.verify(invoice).pay();
    }

    @Test
    public void whenMakeInvoiceActive_thenReturnInvoice() {
        BDDMockito.given(invoiceRepository.getInvoiceFromTransactionId(transactionId)).willReturn(invoice);

        // when
        Invoice currentInvoice = invoiceService.makeInvoiceActive(transactionId);

        // then
        assertEquals(invoice, currentInvoice);
    }

    @Test
    public void givenActiveInvoice_whenMakePayments_thenPayIfPaymentsLeft() {
        // given
        BDDMockito.given(invoiceRepository.getInvoiceFromTransactionId(transactionId)).willReturn(invoice);
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(1);
        this.invoiceService.makeInvoiceActive(transactionId);

        // when
        this.invoiceService.makePayments();

        // then
        Mockito.verify(invoice, Mockito.times(2)).pay();
    }

    @Test
    public void givenActiveInvoice_whenMakePayments_thenSetSaleStatusToPaidIfNoPaymentsLeft() {
        // given
        BDDMockito.given(invoiceRepository.getInvoiceFromTransactionId(transactionId)).willReturn(invoice);
        BDDMockito.given(invoice.getPaymentsLeft()).willReturn(0);
        this.invoiceService.makeInvoiceActive(transactionId);

        // when
        this.invoiceService.makePayments();

        // then
        Mockito.verify(saleRepository).setStatus(transactionId, SaleStatus.PAID);
    }
}
