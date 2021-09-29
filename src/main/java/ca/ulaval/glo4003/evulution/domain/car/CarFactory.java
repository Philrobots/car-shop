package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;

import java.util.List;

public class CarFactory {

    private List<String> possibleCarNames;

    public CarFactory(List<String> possibleCarNames) {
        this.possibleCarNames = possibleCarNames;
    }

    public Car create(String name, String color) {
        if (!possibleCarNames.contains(name) || !color.equals("white"))
            throw new BadCarSpecsException();
        return new Car(name, color);
    }
}
