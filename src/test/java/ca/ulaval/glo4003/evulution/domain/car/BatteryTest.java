package ca.ulaval.glo4003.evulution.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatteryTest {
    private final String A_TYPE = "STANDARD";
    private final String A_BASE_NRCAN_RANGE = "400";
    private final Integer A_CAPACITY = 60;
    private final BigDecimal A_PRICE = new BigDecimal(15000);
    private final Integer TIME_TO_PRODUCE = 3;

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
        assertEquals(400, range);
    }

    @Test
    public void whenGetToProduceAsInt_shouldReturnProductionTimeAsInteger() {
        // when
        Integer productionTime = battery.getTimeToProduce();

        // then
        assertEquals(3, productionTime);
    }
}
