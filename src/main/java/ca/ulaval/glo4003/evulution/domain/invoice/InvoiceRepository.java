package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public interface InvoiceRepository {
    void addInvoice(TransactionId transactionId, Invoice invoice);

    Invoice getInvoiceFromTransactionId(TransactionId transactionId);
}
