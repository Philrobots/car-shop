package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class BatteryFactoryTest {
    private static final String A_VALID_NAME = "LONGUE AUTONOMIE";
    private static final String A_INVALID_NAME = "a_bad_type";
    private static final String A_TIME_TO_PRODUCE = "2";
    private static final String A_NRCAN_RANGE = "2000";
    private static final Integer A_CAPACITY = 5;
    private static final Integer A_PRICE = 1000;

    private BatteryFactory batteryFactory;

    @Mock
    private BatteryInformationDto batteryMapperDto;

    @BeforeEach
    public void setup() {
        batteryFactory = new BatteryFactory(Collections.singletonList(batteryMapperDto));
    }


     @Test
     public void givenValidBattery_whenCreate_thenReturnsBattery() throws BadCarSpecsException {
         // given
         batteryMapperDto.name = A_VALID_NAME;
         batteryMapperDto.base_NRCAN_range = A_NRCAN_RANGE;
         batteryMapperDto.capacity = A_CAPACITY;
         batteryMapperDto.price = A_PRICE;
         batteryMapperDto.time_to_produce = A_TIME_TO_PRODUCE;

         // when
         Battery battery = batteryFactory.create(A_VALID_NAME);

         // then
         Assertions.assertNotNull(battery);
         }

     @Test
     public void givenInvalidBattery_whenCreate_thenThrowsBadCarSpecsException() {
        // given
         batteryMapperDto.name = A_VALID_NAME;

        // when & then
        Assertions.assertThrows(BadCarSpecsException.class, () -> batteryFactory.create(
            A_INVALID_NAME));
    }

}
