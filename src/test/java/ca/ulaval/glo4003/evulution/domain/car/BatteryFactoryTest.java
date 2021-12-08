package ca.ulaval.glo4003.evulution.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class BatteryFactoryTest {
    private static final String A_GOOD_NAME = "LONGUE AUTONOMIE";
    private static final String A_BAD_NAME = "a_bad_type";
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

    /*
     *
     * @Test public void givenGoodType_whenCreate_thenReturnsBattery() throws BadCarSpecsException { // given
     * BDDMockito.given(batteryMapperDto.name).willReturn(A_GOOD_NAME);
     * BDDMockito.given(batteryMapperDto.base_NRCAN_range).willReturn(A_NRCAN_RANGE);
     * BDDMockito.given(batteryMapperDto.capacity).willReturn(A_CAPACITY);
     * BDDMockito.given(batteryMapperDto.price).willReturn(A_PRICE);
     * BDDMockito.given(batteryMapperDto.time_to_produce).willReturn(A_TIME_TO_PRODUCE);
     *
     * // when Battery battery = batteryFactory.create(A_GOOD_NAME);
     *
     * // then assertNotNull(battery); }
     *
     * @Test public void givenBadType_whenCreate_thenThrowsBadCarSpecsException() { // given
     * BDDMockito.given(batteryMapperDto.name).willReturn(A_GOOD_NAME);
     *
     * // then assertThrows(BadCarSpecsException.class, () -> batteryFactory.create(A_BAD_NAME)); }
     *
     */
}
