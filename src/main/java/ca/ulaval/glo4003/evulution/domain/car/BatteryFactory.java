package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;

import java.util.List;

public class BatteryFactory {
    private List<String> possibleBatteryTypes;
    public BatteryFactory(List<String> stringList) {
        this.possibleBatteryTypes = stringList;
    }

    public Battery create(String type, Car car) {
        if (!possibleBatteryTypes.contains(type))
            throw new BadCarSpecsException();
        return new Battery(type, car.getRate());
    }


}
