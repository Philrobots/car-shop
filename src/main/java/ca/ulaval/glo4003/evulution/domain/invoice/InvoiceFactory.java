package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;

public class InvoiceFactory {

    public Invoice create(int bank_no, int account_no, String frequency) {
        for (Frequency freq : Frequency.values()) {
            if (freq.getFrequency().equals(frequency)) {
                return new Invoice(bank_no, account_no, freq);
            }
        }
        throw new InvalidInvoiceException();
    }
}
