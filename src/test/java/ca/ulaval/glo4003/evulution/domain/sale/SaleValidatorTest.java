package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.IncompleteSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MismatchAccountIdWithSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaleValidatorTest {
    private static final Integer A_SALE_ID_REQUEST = 2;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private SaleIdFactory saleIdFactory;

    @Mock
    private SaleId saleId;

    @Mock
    private Sale sale;

    @Mock
    private AccountId accountId;

    @Mock
    private DeliveryId deliveryId;

    private SaleValidator saleValidator;

    @BeforeEach
    public void setUp() {
        this.saleValidator = new SaleValidator(saleRepository, deliveryRepository, saleIdFactory);
    }

    @Test
    public void whenValidateSaleOwner_thenSaleRepositoryGetsSale()
            throws SaleNotFoundException, MismatchAccountIdWithSaleException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleValidator.validateSaleOwner(saleId, accountId);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenValidateSaleOwner_thenSaleVerifiesAccountId()
            throws SaleNotFoundException, MismatchAccountIdWithSaleException {
        // given
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleValidator.validateSaleOwner(saleId, accountId);

        // then
        Mockito.verify(sale).verifyAccountId(accountId);
    }

    @Test
    public void whenValidateCompleteStatus_thenDeliveryRepositoryGetsSaleId()
            throws SaleNotFoundException, DeliveryNotFoundException, IncompleteSaleException {
        // given
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleValidator.validateCompleteStatus(deliveryId);

        // then
        Mockito.verify(deliveryRepository).getSaleId(deliveryId);
    }

    @Test
    public void whenValidateCompleteStatus_thenSaleRepositoryGetsSale()
            throws SaleNotFoundException, DeliveryNotFoundException, IncompleteSaleException {
        // given
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleValidator.validateCompleteStatus(deliveryId);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenValidateCompleteStatus_thenSaleVerifiesIsCompleted()
            throws SaleNotFoundException, DeliveryNotFoundException, IncompleteSaleException {
        // given
        BDDMockito.given(deliveryRepository.getSaleId(deliveryId)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);

        // when
        saleValidator.validateCompleteStatus(deliveryId);

        // then
        Mockito.verify(sale).verifyIsCompleted();
    }

    @Test
    public void whenValidateNoteCompleteStatus_thenSaleIdFactoryCreatesFromInt()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        // when
        this.saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // then
        Mockito.verify(saleIdFactory).createFromInt(A_SALE_ID_REQUEST);
    }

    @Test
    public void whenValidateNotCompleteStatus_thenSaleRepositoryGetsSale()
            throws SaleNotFoundException, SaleAlreadyCompleteException {

        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        // when
        this.saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // then
        Mockito.verify(saleRepository).getSale(saleId);
    }

    @Test
    public void whenValidateNotCompleteStatus_thenSaleVerifiesNotCompleted()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        // when
        this.saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // then
        Mockito.verify(sale).verifyNotCompleted();
    }

    @Test
    public void whenValidateNotCompleteStatus_thenReturnsSaleId()
            throws SaleNotFoundException, SaleAlreadyCompleteException {
        // given
        BDDMockito.given(saleIdFactory.createFromInt(A_SALE_ID_REQUEST)).willReturn(saleId);
        BDDMockito.given(saleRepository.getSale(saleId)).willReturn(sale);
        // when
        SaleId currentSaleId = this.saleValidator.validateNotCompleteStatus(A_SALE_ID_REQUEST);

        // then
        Assertions.assertEquals(saleId, currentSaleId);
    }
}
