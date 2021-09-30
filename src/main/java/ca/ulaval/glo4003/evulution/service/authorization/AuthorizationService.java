package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;

public class AuthorizationService {
    private final TokenAssembler tokenAssembler;
    private final TokenRepository tokenRepository;
    private SaleRepository saleRepository;
    private TransactionIdFactory transactionIdFactory;

    public AuthorizationService(TokenAssembler tokenAssembler, TokenRepository tokenRepository, SaleRepository saleRepository, TransactionIdFactory transactionIdFactory) {
        this.tokenAssembler = tokenAssembler;
        this.tokenRepository = tokenRepository;
        this.saleRepository = saleRepository;
        this.transactionIdFactory = transactionIdFactory;
    }

    public void validateToken(TokenDto tokenDto) {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.tokenRepository.validateToken(token);
    }

    public void validateTokenWithTransactionId(TokenDto tokenDto, int transactionId) {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.tokenRepository.validateToken(token);
        String email = this.tokenRepository.getEmail(token);
        Sale sale = this.saleRepository.getSale(transactionIdFactory.createFromInt(transactionId));
        if (!sale.getEmail().equals(email)) throw new UnauthorizedRequestException();
    }
}
