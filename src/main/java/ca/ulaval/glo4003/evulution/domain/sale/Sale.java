package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;

import java.math.BigDecimal;

public class Sale {
    private final AccountId accountId;
    private final SaleId saleId;
    private final InvoiceFactory invoiceFactory;
    private Invoice invoice;
    private BigDecimal price = BigDecimal.ZERO;
    private SaleStatus status;

    public Sale(AccountId accountId, SaleId saleId, InvoiceFactory invoiceFactory) {
        this.accountId = accountId;
        this.saleId = saleId;
        this.invoiceFactory = invoiceFactory;
        this.status = SaleStatus.CREATED;
    }

    public AccountId getAccountId() {
        return this.accountId;
    }

    public void verifyAccountId(AccountId accountId) throws MismatchAccountIdWithSaleException {
        if (!this.accountId.equals(accountId))
            throw new MismatchAccountIdWithSaleException();
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public void setStatus(SaleStatus saleStatus) {
        this.status = saleStatus;
    }

    public void completeSale(int bankNumber, int accountNumber, String frequency)
            throws SaleAlreadyCompleteException, InvalidInvoiceException {
        if (this.status == SaleStatus.COMPLETED) {
            throw new SaleAlreadyCompleteException();
        }
        this.invoice = invoiceFactory.create(bankNumber, accountNumber, frequency, price);
        this.status = SaleStatus.COMPLETED;
    }

    public void verifyIsCompleted() throws IncompleteSaleException {
        if (!status.equals(SaleStatus.COMPLETED))
            throw new IncompleteSaleException();
    }

    public void verifyNotCompleted() throws SaleAlreadyCompleteException {
        if (status.equals(SaleStatus.COMPLETED))
            throw new SaleAlreadyCompleteException();
    }

    public void addPrice(BigDecimal price) {
        this.price = this.price.add(price);
    }

    public Invoice activateInvoice(InvoicePayment invoicePayment) throws IncompleteSaleException {
        verifyIsCompleted();
        invoicePayment.makeInvoiceActive(this.saleId, this.invoice);
        this.invoice.pay();
        return this.invoice;
    }
}
