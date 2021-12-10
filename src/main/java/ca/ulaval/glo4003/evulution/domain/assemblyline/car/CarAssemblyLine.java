package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;

import java.util.List;

public interface CarAssemblyLine {
    void addProduction(CarProduction carProduction) throws CarNotAssociatedWithManufactureException;

    void advance();

    void shutdown();

    void reactivate();

    void startNext();

    void transferWaitingList(CarAssemblyLine carAssemblyLine);

    List<CarProduction> getWaitingList();
}
