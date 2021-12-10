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
class CarFactoryTest {
    private static final String A_VALID_NAME = "Vandry";
    private static final String A_VALID_COLOR = "white";
    private static final String A_STYLE = "Compact";
    private static final String A_TIME_TO_PRODUCE = "3";
    private static final String AN_INVALID_NAME = "an_invalid_name";
    private static final String AN_INVALID_COLOR = "an_invalid_color";
    private static final Integer A_PRICE = 27000;
    private static final Integer AN_EFFICIENCY_EQUIVALENCE_RATE = 100;

    private CarFactory carFactory;

    @Mock
    private ModelInformationDto modelInformationDto;

    @BeforeEach
    public void setup() {
        carFactory = new CarFactory(Collections.singletonList(modelInformationDto));
    }

    @Test
    public void givenValidSpecs_whenCreate_thenReturnsCar() throws BadCarSpecsException {
        // given
        modelInformationDto.name = A_VALID_NAME;
        modelInformationDto.base_price = A_PRICE;
        modelInformationDto.style = A_STYLE;
        modelInformationDto.effeciency_equivalence_rate = AN_EFFICIENCY_EQUIVALENCE_RATE;
        modelInformationDto.time_to_produce = A_TIME_TO_PRODUCE;

        // when
        Car car = carFactory.create(A_VALID_NAME, A_VALID_COLOR);

        // then
        Assertions.assertNotNull(car);
    }

    @Test
    public void givenInvalidName_whenCreate_thenThrowsBadCarSpecsException() {
        // given
        modelInformationDto.name = A_VALID_NAME;

        // then
        Assertions.assertThrows(BadCarSpecsException.class, () -> carFactory.create(AN_INVALID_NAME, A_VALID_COLOR));
    }

    @Test
    public void givenInvalidColor_whenCreate_thenThrowsBadCarSpecsException() {
        // given
        modelInformationDto.name = A_VALID_NAME;

        // then
        Assertions.assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_VALID_NAME, AN_INVALID_COLOR));
    }
}
