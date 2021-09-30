package ca.ulaval.glo4003.evulution.domain.car.battery;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.car.BatteryInformationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BatteryFactoryTest {

    private static final String A_GOOD_NAME = "LONGUE AUTONOMIE";
    private static final String A_BAD_NAME = "a_bad_type";

    private BatteryFactory batteryFactory;

    private BatteryInformationDto batteryMapperDto;

    @BeforeEach
    public void setup() {
        batteryMapperDto = new BatteryInformationDto();
        batteryMapperDto.name = A_GOOD_NAME;
        batteryFactory = new BatteryFactory(Collections.singletonList(batteryMapperDto));
    }

    @Test
    public void givenGoodType_whenCreate_thenReturnsBattery() {

        // when
        Battery battery = batteryFactory.create(A_GOOD_NAME);

        // then
        assertNotNull(battery);

    }

    @Test
    public void givenBadType_whenCreate_thenThrowsBadCarSpecsException() {

        // then
        assertThrows(BadCarSpecsException.class, () -> batteryFactory.create(A_BAD_NAME));
    }
}
