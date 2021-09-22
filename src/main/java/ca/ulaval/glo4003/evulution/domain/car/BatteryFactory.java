package ca.ulaval.glo4003.evulution.domain.car;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.exception.BadCarSpecsException;

public class BatteryFactory {

    public Battery create(String type) {
        if (!type.equals("Longue Autonomie"))
            throw new BadCarSpecsException();
        return new Battery(type);
    }
}
