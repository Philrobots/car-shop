package ca.ulaval.glo4003.evulution.service.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.HashMap;

public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SaleRepository saleRepository;
    private final HashMap<TransactionId, Invoice> activeInvoices = new HashMap<>();

    public InvoiceService(InvoiceRepository invoiceRepository, SaleRepository saleRepository) {
        this.invoiceRepository = invoiceRepository;
        this.saleRepository = saleRepository;
    }

    public Invoice makeInvoiceActive(TransactionId transactionId) {
        Invoice invoice = invoiceRepository.getInvoiceFromTransactionId(transactionId);
        invoice.pay();
        this.activeInvoices.put(transactionId, invoice);
        return invoice;
    }

    public void makePayments() {
        for (var entry : activeInvoices.entrySet()) {
            if (entry.getValue().getPaymentsLeft() > 0) {
                entry.getValue().pay();
            } else {
                saleRepository.setStatus(entry.getKey(), SaleStatus.PAID);
                activeInvoices.remove(entry.getKey());
            }
        }
    }
}
