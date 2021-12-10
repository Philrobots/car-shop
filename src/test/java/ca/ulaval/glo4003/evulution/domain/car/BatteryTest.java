package ca.ulaval.glo4003.evulution.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatteryTest {
    private static final String A_TYPE = "STANDARD";
    private static final String A_BASE_NRCAN_RANGE = "400";
    private static final Integer A_CAPACITY = 60;
    private static final BigDecimal A_PRICE = new BigDecimal(15000);
    private static final Integer TIME_TO_PRODUCE = 3;
    private static final Integer EXPECTED_RANGE = 400;
    private Battery battery;

    @BeforeEach
    public void setUp() {
        this.battery = new Battery(A_TYPE, A_BASE_NRCAN_RANGE, A_CAPACITY, A_PRICE, TIME_TO_PRODUCE);
    }

    @Test
    public void givenAnEfficiency_whenCalculateEstimatedRange_shouldReturnAppropriateRange() {
        // given
        Integer efficiency = 100;

        // when
        Integer range = this.battery.calculateEstimatedRange(efficiency);

        // then
        assertEquals(EXPECTED_RANGE, range);
    }
}
