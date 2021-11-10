package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.invoice.payments.Biweekly;
import ca.ulaval.glo4003.evulution.domain.invoice.payments.Monthly;
import ca.ulaval.glo4003.evulution.domain.invoice.payments.Weekly;

import java.math.BigDecimal;

public class InvoiceFactory {

    public Invoice create(int bank_no, int account_no, String frequency, BigDecimal balance) {
        switch (frequency) {
        case "monthly":
            return new Invoice(bank_no, account_no, Frequency.MONTHLY, new Monthly(balance));
        case "biweekly":
            return new Invoice(bank_no, account_no, Frequency.BIWEEKLY, new Biweekly(balance));
        case "weekly":
            return new Invoice(bank_no, account_no, Frequency.WEEKLY, new Weekly(balance));
        default:
            throw new InvalidInvoiceException();
        }
    }
}
