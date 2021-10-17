package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeliveryFactoryTest {
    private static final String A_MODE = "At campus";
    private static final String A_LOCATION = "Vachon";
    private DeliveryFactory deliveryFactory;

    @BeforeEach
    public void setUp() {
        deliveryFactory = new DeliveryFactory(Collections.singletonList(A_LOCATION));
    }

    @Test
    public void whenCreate_thenReturnsDelivery() {
        // when
        Delivery delivery = deliveryFactory.create(A_MODE, A_LOCATION);

        // then
        assertNotNull(delivery);
    }

    @Test
    public void givenInvalidMode_whenCreate_thenThrowsBadDeliveryModeException() {
        // given
        String invalidMode = "Online";

        // when
        Executable create = () -> this.deliveryFactory.create(invalidMode, A_LOCATION);

        // then
        assertThrows(BadDeliveryModeException.class, create);
    }

    @Test
    public void givenInvalidLocation_whenCreate_thenThrowBadDeliveryLocationException() {
        // given
        String invalidLocation = "Invalid Location";

        // when
        Executable create = () -> this.deliveryFactory.create(A_MODE, invalidLocation);

        // then
        assertThrows(BadDeliveryLocationException.class, create);
    }
}
