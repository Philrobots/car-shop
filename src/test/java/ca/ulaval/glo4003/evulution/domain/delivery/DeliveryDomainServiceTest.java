package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryDomainServiceTest {
    private final static DeliveryId A_DELIVERY_ID = new DeliveryId(564);
    private final static String A_DELIVERY_MODE = "At campus";
    private final static String A_DELIVERY_LOCATION = "Pouliot";

    @Mock
    private DeliveryDetailsFactory deliveryDetailsFactory;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryDetails deliveryDetails;

    @Mock
    private Delivery delivery;

    private DeliveryDomainService deliveryDomainService;

    @BeforeEach
    public void setUp() {
        deliveryDomainService = new DeliveryDomainService(deliveryDetailsFactory, deliveryRepository);
    }

    @Test
    public void whenSetDeliveryModeLocationAndConfirmDelivery_thenSetDeliveryDetails() throws DeliveryNotFoundException, DeliveryIncompleteException, BadDeliveryModeException, BadDeliveryLocationException {
        //given
        BDDMockito.given(deliveryDetailsFactory.create(A_DELIVERY_MODE, A_DELIVERY_LOCATION)).willReturn(deliveryDetails);
        BDDMockito.given(deliveryRepository.getDelivery(A_DELIVERY_ID)).willReturn(delivery);

        //when
        deliveryDomainService.setDeliveryModeLocationAndConfirmDelivery(A_DELIVERY_ID, A_DELIVERY_MODE, A_DELIVERY_LOCATION);

        //then
        BDDMockito.verify(delivery).setDeliveryDetails(deliveryDetails);
    }

    @Test
    public void whenSetDeliveryModeLocationAndConfirmDelivery_thenUpdatesRepo() throws DeliveryNotFoundException, DeliveryIncompleteException, BadDeliveryModeException, BadDeliveryLocationException {
        //given
        BDDMockito.given(deliveryDetailsFactory.create(A_DELIVERY_MODE, A_DELIVERY_LOCATION)).willReturn(deliveryDetails);
        BDDMockito.given(deliveryRepository.getDelivery(A_DELIVERY_ID)).willReturn(delivery);

        //when
        deliveryDomainService.setDeliveryModeLocationAndConfirmDelivery(A_DELIVERY_ID, A_DELIVERY_MODE, A_DELIVERY_LOCATION);

        //then
        BDDMockito.verify(deliveryRepository).updateDelivery(delivery);
    }

    @Test
    public void whenCompleteDelivery_thenCompletesDelivery() throws DeliveryNotFoundException, DeliveryIncompleteException {
        //given
        BDDMockito.given(deliveryRepository.getDelivery(A_DELIVERY_ID)).willReturn(delivery);

        //when
        deliveryDomainService.completeDelivery(A_DELIVERY_ID);

        //then
        BDDMockito.verify(delivery).completeDelivery();
    }

    @Test
    public void whenCompleteDelivery_thenUpdatesRepo() throws DeliveryNotFoundException, DeliveryIncompleteException {
        //given
        BDDMockito.given(deliveryRepository.getDelivery(A_DELIVERY_ID)).willReturn(delivery);

        //when
        deliveryDomainService.completeDelivery(A_DELIVERY_ID);

        //then
        BDDMockito.verify(deliveryRepository).updateDelivery(delivery);
    }
}