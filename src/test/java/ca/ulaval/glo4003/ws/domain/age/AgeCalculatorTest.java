package ca.ulaval.glo4003.ws.domain.age;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class AgeCalculatorTest {
    private Date A_DATE = new Date();

    private AgeCalculator ageCalculator;

    @BeforeEach
    public void setUp() {
        ageCalculator = new AgeCalculator();
        A_DATE.setYear(100);
    }

    @Test
    public void givenADate_thenCalculateAge_thenShouldReturnTheRightAge() {
        // when
        int age = ageCalculator.calculateAge(A_DATE);

        // then
        assertEquals(age, 21);
    }
}
