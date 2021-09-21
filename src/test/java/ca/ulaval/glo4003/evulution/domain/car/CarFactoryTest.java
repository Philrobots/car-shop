package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarFactoryTest {

    private CarFactory carFactory;
    @BeforeEach
    public void setup(){
        carFactory = new CarFactory();
    }
    @Test
    public void whenBadSpecs_thenThrowsBadCarSpecsException() {
        String badName = "bad name";
        String badColor = "bad color";
        assertThrows(BadCarSpecsException.class, () -> carFactory.create(badName, badColor));
    }
}