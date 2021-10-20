package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeliveryFactoryTest {
    @Mock
    private DeliveryIdFactory deliveryIdFactory;

    private static final Integer assemblyInWeeks = 1;
    private static final String A_MODE = "At campus";
    private static final String A_LOCATION = "Vachon";
    private DeliveryFactory deliveryFactory;

    @BeforeEach
    public void setUp() {
        deliveryFactory = new DeliveryFactory(assemblyInWeeks, deliveryIdFactory);
    }

    @Test
    public void whenCreate_thenReturnsDelivery() {
        // when
        Delivery delivery = deliveryFactory.create();

        // then
        assertNotNull(delivery);
    }
}
