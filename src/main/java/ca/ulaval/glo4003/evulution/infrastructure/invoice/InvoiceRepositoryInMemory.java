package ca.ulaval.glo4003.evulution.infrastructure.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.HashMap;
import java.util.Map;

public class InvoiceRepositoryInMemory implements InvoiceRepository {
    private final Map<TransactionId, Invoice> invoices = new HashMap<>();

    @Override
    public void addInvoice(TransactionId transactionId, Invoice invoice) {
        this.invoices.put(transactionId, invoice);
    }
}
