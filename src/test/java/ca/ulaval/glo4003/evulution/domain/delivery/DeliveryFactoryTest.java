package ca.ulaval.glo4003.evulution.domain.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DeliveryFactoryTest {
    private static final Integer ASSEMBLY_IN_WEEKS = 1;
    private DeliveryFactory deliveryFactory;

    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    @BeforeEach
    public void setUp() {
        deliveryFactory = new DeliveryFactory(ASSEMBLY_IN_WEEKS, deliveryIdFactory);
    }

    @Test
    public void whenCreate_thenReturnsDelivery() {
        // when
        Delivery delivery = deliveryFactory.create();

        // then
        assertNotNull(delivery);
    }
}
