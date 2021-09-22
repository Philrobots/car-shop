package ca.ulaval.glo4003.evulution.domain.car.battery;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BatteryFactoryTest {

    private static final String A_GOOD_TYPE = "Longue Autonomie";
    private static final String A_BAD_TYPE = "a_bad_type";

    private BatteryFactory batteryFactory;

    @BeforeEach
    public void setup() {
        batteryFactory = new BatteryFactory();
    }

    @Test
    public void givenGoodType_whenCreate_thenReturnsBattery() {

        // when
        Battery battery = batteryFactory.create(A_GOOD_TYPE);

        // then
        assertNotNull(battery);
    }

    @Test
    public void givenBadType_whenCreate_thenThrowsBadCarSpecsException() {

        // then
        assertThrows(BadCarSpecsException.class, () -> batteryFactory.create(A_BAD_TYPE));
    }
}
