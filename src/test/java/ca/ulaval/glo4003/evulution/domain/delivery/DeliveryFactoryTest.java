package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryModeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeliveryFactoryTest {

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    private DeliveryFactory deliveryFactory;

    @BeforeEach
    public void setUp() {
        deliveryFactory = new DeliveryFactory(deliveryIdFactory, new ArrayList<>());
    }

    @Test
    public void givenValidInformation_whenCreate_thenReturnsDelivery() {
        // when
        Delivery delivery = deliveryFactory.create();

        // then
        assertNotNull(delivery);
    }
}