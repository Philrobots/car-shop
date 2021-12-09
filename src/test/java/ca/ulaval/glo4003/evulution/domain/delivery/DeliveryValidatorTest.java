package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryValidatorTest {
    private final static DeliveryId A_DELIVERY_ID = new DeliveryId(564);
    private final static AccountId AN_ACCOUNT_ID = new AccountId();

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private Delivery delivery;

    private DeliveryValidator deliveryValidator;

    @BeforeEach
    public void setUp() {
        this.deliveryValidator = new DeliveryValidator(deliveryRepository);
    }

    @Test
    public void whenValidateDeliveryOwner_thenVerifyAccountId()
            throws MismatchAccountIdWithDeliveryException, DeliveryNotFoundException {
        // given
        BDDMockito.given(deliveryRepository.getDelivery(A_DELIVERY_ID)).willReturn(delivery);

        // when
        deliveryValidator.validateDeliveryOwner(A_DELIVERY_ID, AN_ACCOUNT_ID);

        // then
        BDDMockito.verify(delivery).verifyAccountId(AN_ACCOUNT_ID);
    }

}