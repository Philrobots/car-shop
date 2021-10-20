package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exceptions.UnauthorizedRequestException;

public class AuthorizationService {
    private final TokenAssembler tokenAssembler;
    private final TokenRepository tokenRepository;
    private SaleRepository saleRepository;
    private TransactionIdFactory transactionIdFactory;
    private DeliveryIdFactory deliveryIdFactory;

    public AuthorizationService(TokenAssembler tokenAssembler, TokenRepository tokenRepository,
            SaleRepository saleRepository, TransactionIdFactory transactionIdFactory,
            DeliveryIdFactory deliveryIdFactory) {
        this.tokenAssembler = tokenAssembler;
        this.tokenRepository = tokenRepository;
        this.saleRepository = saleRepository;
        this.transactionIdFactory = transactionIdFactory;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public void validateToken(TokenDto tokenDto) {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.tokenRepository.validateToken(token);
    }

    public void validateTokenWithTransactionId(TokenDto tokenDto, int transactionId) {
        Sale sale = this.saleRepository.getSale(transactionIdFactory.createFromInt(transactionId));
        validateSaleAndEmailMatch(sale, getEmailFromTokenDto(tokenDto));
    }

    public void validateTokenWithDeliveryId(TokenDto tokenDto, int deliveryId) {
        Sale sale = this.saleRepository.getSaleFromDeliveryId(deliveryIdFactory.createFromInt(deliveryId));
        validateSaleAndEmailMatch(sale, getEmailFromTokenDto(tokenDto));
    }

    private String getEmailFromTokenDto(TokenDto tokenDto) {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.tokenRepository.validateToken(token);
        return this.tokenRepository.getEmail(token);
    }

    private void validateSaleAndEmailMatch(Sale sale, String email) {
        if (sale == null || !sale.getEmail().equals(email))
            throw new UnauthorizedRequestException();
    }
}
