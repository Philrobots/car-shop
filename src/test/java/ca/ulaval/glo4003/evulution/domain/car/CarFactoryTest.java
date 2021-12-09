package ca.ulaval.glo4003.evulution.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class CarFactoryTest {
    private static final String A_GOOD_NAME = "Vandry";
    private static final String A_GOOD_COLOR = "white";
    private static final String A_STYLE = "Compact";
    private static final String A_TIME_TO_PRODUCE = "3";
    private static final String A_BAD_NAME = "a_bad_name";
    private static final String A_BAD_COLOR = "a_bad_color";
    private static final Integer A_PRICE = 27000;
    private static final Integer AN_EFFICIENCY_EQUIVALENCE_RATE = 100;

    private CarFactory carFactory;

    @Mock
    private ModelInformationDto modelMapperDto;

    @BeforeEach
    public void setup() {
        carFactory = new CarFactory(Collections.singletonList(modelMapperDto));
    } /*
       *
       * @Test public void givenGoodSpecs_whenCreate_thenReturnsCar() throws BadCarSpecsException { // given
       * BDDMockito.given(modelMapperDto.name).willReturn(A_GOOD_NAME);
       * BDDMockito.given(modelMapperDto.style).willReturn(A_STYLE);
       * BDDMockito.given(modelMapperDto.effeciency_equivalence_rate).willReturn(AN_EFFICIENCY_EQUIVALENCE_RATE);
       * BDDMockito.given(modelMapperDto.base_price).willReturn(A_PRICE);
       * BDDMockito.given(modelMapperDto.time_to_produce).willReturn(A_TIME_TO_PRODUCE);
       *
       * // when Car car = carFactory.create(A_GOOD_NAME, A_GOOD_COLOR);
       *
       * // then assertNotNull(car); }
       *
       * @Test public void givenBadName_whenCreate_thenThrowsBadCarSpecsException() { // given
       * BDDMockito.given(modelMapperDto.name).willReturn(A_GOOD_NAME);
       *
       * // then assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_BAD_NAME, A_GOOD_COLOR)); }
       *
       * @Test public void givenBadColor_whenCreate_thenThrowsBadCarSpecsException() { // given
       * BDDMockito.given(modelMapperDto.name).willReturn(A_GOOD_NAME);
       *
       * // then assertThrows(BadCarSpecsException.class, () -> carFactory.create(A_GOOD_NAME, A_BAD_COLOR)); }
       */
}
