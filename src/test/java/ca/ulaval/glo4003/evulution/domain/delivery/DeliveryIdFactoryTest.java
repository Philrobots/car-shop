package ca.ulaval.glo4003.evulution.domain.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeliveryIdFactoryTest {
    private DeliveryIdFactory deliveryIdFactory;

    @BeforeEach
    public void setUp() {
        deliveryIdFactory = new DeliveryIdFactory();
    }

    @Test
    public void whenCreate_thenADeliveryIdIsCreated() {
        // when
        DeliveryId deliveryId = deliveryIdFactory.create();

        // then
        assertNotNull(deliveryId);
    }

    @Test
    public void givenDeliveryId_whenCreateFromInt_thenADeliveryIdIsCreated() {
        // given
        int deliveryIdInt = 1;

        // when
        DeliveryId deliveryId = deliveryIdFactory.createFromInt(deliveryIdInt);

        // then
        assertNotNull(deliveryId);
    }
}
