package ca.ulaval.glo4003.evulution.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    private final String A_NAME = "Vandry";
    private final String A_STYLE = "Compact";
    private final Integer AN_EFFICIENCY_EQUIVALENCE_RATE = 100;
    private final Integer A_BASE_PRICE = 27000;
    private final String A_TIME_TO_PRODUCE = "3";
    private final String A_COLOR = "white";

    private Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car(A_NAME, A_STYLE, AN_EFFICIENCY_EQUIVALENCE_RATE, A_BASE_PRICE, A_TIME_TO_PRODUCE, A_COLOR);
    }

    @Test
    void whenGetTimeToProduceAsInt_thenReturnProductionTimeAsInteger() {
        // when
        Integer productionTime = car.getTimeToProduceAsInt();

        // then
        assertEquals(3, productionTime);
    }
}
