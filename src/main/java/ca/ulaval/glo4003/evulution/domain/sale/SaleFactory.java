package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;

public class SaleFactory {
    private final SaleIdFactory saleIdFactory;
    private final InvoiceFactory invoiceFactory;
    private final TokenRepository tokenRepository;

    public SaleFactory(SaleIdFactory saleIdFactory, InvoiceFactory invoiceFactory, TokenRepository tokenRepository) {
        this.saleIdFactory = saleIdFactory;
        this.invoiceFactory = invoiceFactory;
        this.tokenRepository = tokenRepository;
    }

    public Sale create(Token token) throws TokenNotFoundException {
        AccountId accountId = this.tokenRepository.getAccountId(token);
        SaleId saleId = this.saleIdFactory.create();
        return new Sale(accountId, saleId, this.invoiceFactory);
    }
}
