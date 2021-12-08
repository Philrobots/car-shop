package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.util.HashMap;

public class InvoicePayment {
    private final SaleRepository saleRepository;
    private final HashMap<SaleId, Invoice> activeInvoices = new HashMap<>();

    public InvoicePayment(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void makeInvoiceActive(SaleId saleId, Invoice invoice) {
        this.activeInvoices.put(saleId, invoice);
    }

    public void makePayments() throws SaleNotFoundException {
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
