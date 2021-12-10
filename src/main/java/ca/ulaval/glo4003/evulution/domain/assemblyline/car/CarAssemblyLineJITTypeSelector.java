package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.car.CarType;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;

public class CarAssemblyLineJITTypeSelector {
    private CarType currentType = CarType.SUBCOMPACT;
    private CarProductionFactory carProductionFactory;

    public CarAssemblyLineJITTypeSelector(CarProductionFactory carProductionFactory) {
        this.carProductionFactory = carProductionFactory;
    }

    public CarProduction getNextCarProduction() {
        CarProduction carProduction = null;
        if (currentType.equals(CarType.SUBCOMPACT)) {
            carProduction = carProductionFactory.createCompact();
            currentType = CarType.COMPACT;
        } else if (currentType.equals(CarType.COMPACT)) {
            carProduction = carProductionFactory.createLuxury();
            currentType = CarType.LUXURY;
        } else if (currentType.equals(CarType.LUXURY)) {
            carProduction = carProductionFactory.createSubCompact();
            currentType = CarType.SUBCOMPACT;
        }
        return carProduction;
    }
}
