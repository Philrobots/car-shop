package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryDetailsFactoryTest {
    private static final String A_LOCATION = "Vachon";
    private static final String A_MODE = "At campus";

    private DeliveryDetailsFactory deliveryDetailsFactory;

    @BeforeEach
    void setUp() {
        this.deliveryDetailsFactory = new DeliveryDetailsFactory(List.of(A_LOCATION));
    }

    @Test
    public void givenInvalidMode_whenCreate_thenThrowsBadDeliveryModeException() {
        // given
        String invalidMode = "Online";

        // when
        Executable create = () -> this.deliveryDetailsFactory.create(invalidMode, A_LOCATION);

        // then
        assertThrows(BadDeliveryModeException.class, create);
    }

    @Test
    public void givenInvalidLocation_whenCreate_thenThrowBadDeliveryLocationException() {
        // given
        String invalidLocation = "Invalid Location";

        // when
        Executable create = () -> this.deliveryDetailsFactory.create(A_MODE, invalidLocation);

        // then
        assertThrows(BadDeliveryLocationException.class, create);
    }
}
