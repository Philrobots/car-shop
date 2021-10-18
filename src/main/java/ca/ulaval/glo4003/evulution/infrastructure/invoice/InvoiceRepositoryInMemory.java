package ca.ulaval.glo4003.evulution.infrastructure.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;

import java.util.HashMap;
import java.util.Map;

public class InvoiceRepositoryInMemory implements InvoiceRepository {
    private final Map<String, Invoice> invoices = new HashMap<>();

    @Override
    public void addInvoice(String email, Invoice invoice) {
        this.invoices.put(email, invoice);
    }
}
