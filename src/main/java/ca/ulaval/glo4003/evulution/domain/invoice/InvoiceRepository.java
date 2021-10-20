package ca.ulaval.glo4003.evulution.domain.invoice;

public interface InvoiceRepository {
    void addInvoice(String email, Invoice invoice);
}
