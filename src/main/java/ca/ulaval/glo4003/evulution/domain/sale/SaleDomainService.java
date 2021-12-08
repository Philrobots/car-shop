package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.invoice.exceptions.InvalidInvoiceException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.math.BigDecimal;

public class SaleDomainService {

    private SaleRepository saleRepository;
    private AccountRepository accountRepository;
    private InvoicePayment invoicePayment;

    public SaleDomainService(SaleRepository saleRepository, AccountRepository accountRepository,
            InvoicePayment invoicePayment) {
        this.saleRepository = saleRepository;
        this.accountRepository = accountRepository;
        this.invoicePayment = invoicePayment;
    }

    public void addPrice(SaleId saleId, BigDecimal price) throws SaleNotFoundException {
        Sale sale = saleRepository.getSale(saleId);
        sale.addPrice(price);
        saleRepository.registerSale(sale);
    }

    public void completeSale(SaleId saleId, int bankNumber, int accountNumber, String frequency)
            throws SaleNotFoundException, SaleAlreadyCompleteException, InvalidInvoiceException {
        Sale sale = this.saleRepository.getSale(saleId);
        sale.completeSale(bankNumber, accountNumber, frequency);
        saleRepository.registerSale(sale);
    }

    public Invoice activateInvoice(SaleId saleId) throws SaleNotFoundException, IncompleteSaleException {
        Sale sale = this.saleRepository.getSale(saleId);
        Invoice invoice = sale.activateInvoice(invoicePayment);
        saleRepository.registerSale(sale);
        return invoice;
    }

    public String getEmailFromSaleId(SaleId saleId) throws SaleNotFoundException, AccountNotFoundException {
        AccountId accountId = saleRepository.getAccountIdFromSaleId(saleId);
        return accountRepository.getAccount(accountId).getEmail();
    }
}
