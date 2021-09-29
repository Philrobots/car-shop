package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarFactoryTest {

    private static final String A_GOOD_NAME = "Vandry";
    private static final String A_GOOD_COLOR = "white";
    private static final String A_BAD_NAME = "a_bad_name";
    private static final String A_BAD_COLOR = "a_bad_color";

    private CarFactory carFactory;

    @BeforeEach
    public void setup() {
        carFactory = new CarFactory(Arrays.asList("Vandry"));
    }

    @Test
    public void givenGoodSpecs_whenCreate_thenReturnsCar() {

        // when
        Car car = carFactory.create(A_GOOD_NAME, A_GOOD_COLOR);

        // then
        assertNotNull(car);
    }

    @Test
    public void givenBadName_whenCreate_thenThrowsBadCarSpecsException() {
        // then
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_BAD_NAME, A_GOOD_COLOR));
    }

    @Test
    public void givenBadColor_whenCreate_thenThrowsBadCarSpecsException() {
        // then
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_GOOD_NAME, A_BAD_COLOR));
    }

    @Test
    public void givenBadSpecs_whenCreate_thenThrowsBadCarSpecsException() {
        // then
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_BAD_NAME, A_BAD_COLOR));
    }
}