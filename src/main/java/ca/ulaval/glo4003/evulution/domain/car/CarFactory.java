package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;

public class CarFactory {

    public Car create(String name, String color) {
        if (!name.equals("Vandry") || !color.equals("white"))
            throw new BadCarSpecsException();
        return new Car(name, color);
    }
}
