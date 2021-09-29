package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class GenderFactoryTest {

    private final String MEN_GENDER = "M";
    private final String WOMEN_GENDER = "W";
    private final String OTHER_GENDER = "O";
    private final String INVALID_GENDER = "S";


    private GenderFactory genderFactory;

    @BeforeEach
    public void setUp() {
        genderFactory = new GenderFactory();
    }

    @Test
    public void givenAMenSex_whenCreateGender_thenShouldReturnAMenGender() {
        // when
        Gender gender = genderFactory.create(MEN_GENDER);

        // then
        assertEquals(gender, Gender.MEN);
    }

    @Test
    public void givenAWomenSex_whenCreateGender_thenShouldReturnAMenGender() {
        // when
        Gender gender = genderFactory.create(WOMEN_GENDER);

        // then
        assertEquals(gender, Gender.WOMEN);
    }

    @Test
    public void givenAOtherSex_whenCreateGender_thenShouldReturnAMenGender() {
        // when
        Gender gender = genderFactory.create(OTHER_GENDER);

        // then
        assertEquals(gender, Gender.OTHER);
    }

    @Test
    public void givenAInvalidSex_whenCreateGender_thenThrowError() {
        assertThrows(BadInputParameterException.class, () -> genderFactory.create(INVALID_GENDER));
    }



}
