package ca.ulaval.glo4003.evulution.infrastructure.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.invoice.exceptions.InvoiceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class InvoiceRepositoryInMemoryTest {
    private InvoiceRepositoryInMemory invoiceRepositoryInMemory;

    @Mock
    private TransactionId transactionId;

    @Mock
    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        this.invoiceRepositoryInMemory = new InvoiceRepositoryInMemory();
    }

    @Test
    public void whenAddInvoice_thenRepositoryAddsInvoice() {
        // when
        this.invoiceRepositoryInMemory.addInvoice(transactionId, invoice);

        // then
        Invoice currentInvoice = this.invoiceRepositoryInMemory.getInvoiceFromTransactionId(transactionId);
        assertEquals(invoice, currentInvoice);
    }

    @Test
    public void givenEmptyInvoiceRepository_whenGetInvoice_thenThrowsInvoiceNotFoundException() {
        // when
        Executable result = () -> invoiceRepositoryInMemory.getInvoiceFromTransactionId(transactionId);

        // then
        assertThrows(InvoiceNotFoundException.class, result);
    }
}
