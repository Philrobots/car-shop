package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryValidator;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleIdFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.authorization.exceptions.ServiceUnauthorizedException;

public class AuthorizationService {
    private final TokenAssembler tokenAssembler;
    private final TokenRepository tokenRepository;
    private final SaleIdFactory saleIdFactory;
    private final DeliveryIdFactory deliveryIdFactory;
    private final AccountValidator accountValidator;
    private final DeliveryValidator deliveryValidator;
    private final SaleValidator saleValidator;

    public AuthorizationService(TokenAssembler tokenAssembler, TokenRepository tokenRepository,
                                SaleIdFactory saleIdFactory, DeliveryIdFactory deliveryIdFactory, AccountValidator accountValidator,
                                DeliveryValidator deliveryValidator, SaleValidator saleValidator) {
        this.tokenAssembler = tokenAssembler;
        this.tokenRepository = tokenRepository;
        this.saleIdFactory = saleIdFactory;
        this.deliveryIdFactory = deliveryIdFactory;
        this.accountValidator = accountValidator;
        this.deliveryValidator = deliveryValidator;
        this.saleValidator = saleValidator;
    }

    private AccountId getAccountId(TokenDto tokenDto) {
        try {
            Token token = this.tokenAssembler.assembleTokenFromDto(tokenDto);
            return this.tokenRepository.getAccountId(token);
        } catch (TokenNotFoundException e) {
            throw new ServiceUnauthorizedException();
        }
    }

    public void validateToken(TokenDto tokenDto) {
        getAccountId(tokenDto);
    }

    public void validateAdminToken(TokenDto tokenDto) {
        try {
            AccountId accountId = getAccountId(tokenDto);
            this.accountValidator.validateAdminAccount(accountId);
        } catch (UserIsNotAdminException | AccountNotFoundException e) {
            throw new ServiceUnauthorizedException();
        }
    }

    public void validateTokenWithSaleId(TokenDto tokenDto, int saleId) {
        try {
            AccountId accountId = getAccountId(tokenDto);
            this.saleValidator.validateSaleOwner(saleIdFactory.createFromInt(saleId), accountId);
        } catch (MismatchAccountIdWithSaleException | SaleNotFoundException e) {
            throw new ServiceUnauthorizedException();
        }
    }

    public void validateTokenWithDeliveryId(TokenDto tokenDto, int deliveryId) {
        try {
            AccountId accountId = getAccountId(tokenDto);
            this.deliveryValidator.validateDeliveryOwner(deliveryIdFactory.createFromInt(deliveryId), accountId);
        } catch (MismatchAccountIdWithDeliveryException | DeliveryNotFoundException e) {
            throw new ServiceUnauthorizedException();
        }
    }
}
