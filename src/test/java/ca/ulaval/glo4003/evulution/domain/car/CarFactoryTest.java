package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarFactoryTest {

    private static final String A_GOOD_NAME = "Vandry";
    private static final String A_GOOD_COLOR = "white";
    private static final String A_BAD_NAME = "a_bad_name";
    private static final String A_BAD_COLOR = "a_bad_color";

    private CarFactory carFactory;

    private ModelInformationDto modelMapperDto;

    @BeforeEach
    public void setup() {
        modelMapperDto = new ModelInformationDto();
        modelMapperDto.name = A_GOOD_NAME;
        carFactory = new CarFactory(Collections.singletonList(modelMapperDto));
    }

    @Test
    public void givenGoodSpecs_whenCreate_thenReturnsCar() {

        Car car = carFactory.create(A_GOOD_NAME, A_GOOD_COLOR);

        // then
        assertNotNull(car);
    }

    @Test
    public void givenBadName_whenCreate_thenThrowsBadCarSpecsException() { // then
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_BAD_NAME, A_GOOD_COLOR));
    }

    @Test
    public void givenBadColor_whenCreate_thenThrowsBadCarSpecsException() { // then
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_GOOD_NAME, A_BAD_COLOR));
    }

    @Test
    public void givenBadSpecs_whenCreate_thenThrowsBadCarSpecsException() { // // then //
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_BAD_NAME, A_BAD_COLOR));
    }

}
