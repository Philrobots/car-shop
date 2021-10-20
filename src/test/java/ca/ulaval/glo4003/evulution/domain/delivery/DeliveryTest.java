package ca.ulaval.glo4003.evulution.domain.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DeliveryTest {
    private static final Integer AN_ASSEMBLY_TIME_IN_WEEKS = 1;
    private static final Integer A_CAR_ASSEMBLY_TIME_IN_WEEKS = 3;
    private static final Integer A_BATTERY_ASSEMBLY_TIME_IN_WEEKS = 2;

    private Delivery delivery;

    @Mock
    private DeliveryId deliveryId;

    @BeforeEach
    void setUp() {
        this.delivery = new Delivery(deliveryId, AN_ASSEMBLY_TIME_IN_WEEKS);
    }

    @Test
    void givenDeliveryDate_whenAddDelayInWeeks_thenAddDelayToDeliveryDate() {
        // given
        delivery.calculateDeliveryDate(A_CAR_ASSEMBLY_TIME_IN_WEEKS, A_BATTERY_ASSEMBLY_TIME_IN_WEEKS);

        // when
        LocalDate deliveryDate = delivery.addDelayInWeeks(1);

        // then
        assertEquals(deliveryDate, LocalDate.now().plusWeeks(7));
    }

}
