package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryValidator;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
    private static final Integer A_SALE_ID = 2;
    private static final Integer A_DELIVERY_ID = 3;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private SaleIdFactory saleIdFactory;

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @Mock
    private AccountValidator accountValidator;

    @Mock
    private DeliveryValidator deliveryValidator;

    @Mock
    private SaleValidator saleValidator;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private Token token;

    @Mock
    private AccountId accountId;

    @Mock
    private SaleId saleId;

    @Mock
    private DeliveryId deliveryId;

    private AuthorizationService authorizationService;


    @BeforeEach
    void setUp() {
        authorizationService = new AuthorizationService(tokenAssembler,
            tokenRepository,
            saleIdFactory,
            deliveryIdFactory,
            accountValidator,
            deliveryValidator,
            saleValidator);
    }

    @Test
    public void givenTokenNotFoundException_whenValidateToken_thenThrowsServiceUnauthorizedException() throws
        TokenNotFoundException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.doThrow(TokenNotFoundException.class).when(tokenRepository).getAccountId(token);



        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateToken(tokenDto));
    }

    @Test
    public void whenValidateAdminToken_thenAccountValidatorValidatesAdminAccount() throws  TokenNotFoundException,
        AccountNotFoundException, UserIsNotAdminException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);

        // when
        authorizationService.validateAdminToken(tokenDto);

        // then
        Mockito.verify(accountValidator).validateAdminAccount(accountId);
    }

    @Test
    public void givenUserIsNotAdminException_whenValidateAdminToken_thenServiceUnauthorizedException()
        throws TokenNotFoundException, UserIsNotAdminException, AccountNotFoundException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.doThrow(UserIsNotAdminException.class).when(accountValidator).validateAdminAccount(accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateAdminToken(tokenDto));
    }

    @Test
    public void givenAccountNotFoundException_whenValidateAdminToken_thenServiceUnauthorizedException()
        throws TokenNotFoundException, UserIsNotAdminException, AccountNotFoundException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.doThrow(AccountNotFoundException.class).when(accountValidator).validateAdminAccount(accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateAdminToken(tokenDto));
    }


    @Test
    public void whenValidateTokenWithDeliveryId_thenSaleIdFactoryCreatesFromInt() throws
        TokenNotFoundException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);

        // when
        authorizationService.validateTokenWithSaleId(tokenDto, A_SALE_ID);

        // then
        Mockito.verify(saleIdFactory).createFromInt(A_SALE_ID);
    }

    @Test
    public void whenValidateTokenWithSaleId_thenSaleValidatorValidatesSaleOwner() throws
        TokenNotFoundException, SaleNotFoundException, MismatchAccountIdWithSaleException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID)).willReturn(saleId);

        // when
        authorizationService.validateTokenWithSaleId(tokenDto, A_SALE_ID);

        // then
        Mockito.verify(saleValidator).validateSaleOwner(saleId, accountId);
    }

    @Test
    public void givenMismatchAccountIdWithSaleException_whenValidateTokenWithSaleId_thenServiceUnauthorizedException()
        throws TokenNotFoundException, SaleNotFoundException, MismatchAccountIdWithSaleException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID)).willReturn(saleId);
        BDDMockito.doThrow(MismatchAccountIdWithSaleException.class).when(saleValidator).validateSaleOwner(saleId, accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateTokenWithSaleId(tokenDto, A_SALE_ID));
    }

    @Test
    public void givenSaleNotFoundException_whenValidateTokenWithSaleId_thenServiceUnauthorizedException() throws TokenNotFoundException,
        SaleNotFoundException, MismatchAccountIdWithSaleException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID)).willReturn(saleId);
        BDDMockito.doThrow(SaleNotFoundException.class).when(saleValidator).validateSaleOwner(saleId, accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateTokenWithSaleId(tokenDto, A_SALE_ID));
    }

    @Test
    public void whenValidateTokenWithDeliveryId_thenDeliveryIdFactoryCreatesFromInt() throws TokenNotFoundException{
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);

        // when
        authorizationService.validateTokenWithDeliveryId(tokenDto, A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryIdFactory).createFromInt(A_DELIVERY_ID);
    }


    @Test
    public void whenValidateTokenWithDeliveryId_thenDeliveryValidatorValidatesDeliveryOwner() throws TokenNotFoundException,
        DeliveryNotFoundException, MismatchAccountIdWithDeliveryException {
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);

        // when
        authorizationService.validateTokenWithDeliveryId(tokenDto, A_DELIVERY_ID);

        // then
        Mockito.verify(deliveryValidator).validateDeliveryOwner(deliveryId, accountId);
    }

    @Test
    public void givenMismatchAccountIdWithDeliveryException_whenValidateTokenWithDeliveryId_thenServiceUnauthorizedException()
        throws TokenNotFoundException, DeliveryNotFoundException, MismatchAccountIdWithDeliveryException{
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.doThrow(MismatchAccountIdWithDeliveryException.class).when(deliveryValidator).validateDeliveryOwner(deliveryId, accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateTokenWithDeliveryId(tokenDto, A_DELIVERY_ID));
    }

    @Test
    public void givenDeliveryNotFoundException_whenValidateTokenWithDeliveryId_thenServiceUnauthorizedException()
        throws TokenNotFoundException, DeliveryNotFoundException, MismatchAccountIdWithDeliveryException{
        // given
        BDDMockito.given(tokenAssembler.assembleTokenFromDto(tokenDto)).willReturn(token);
        BDDMockito.given(tokenRepository.getAccountId(token)).willReturn(accountId);
        BDDMockito.given(deliveryIdFactory.createFromInt(A_DELIVERY_ID)).willReturn(deliveryId);
        BDDMockito.doThrow(DeliveryNotFoundException.class).when(deliveryValidator).validateDeliveryOwner(deliveryId, accountId);

        // when & then
        Assertions.assertThrows(ServiceUnauthorizedException.class, () -> authorizationService.validateTokenWithDeliveryId(tokenDto, A_DELIVERY_ID));
    }
}
