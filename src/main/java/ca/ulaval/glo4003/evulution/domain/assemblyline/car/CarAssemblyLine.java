package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.List;

public interface CarAssemblyLine {
    void addProduction(CarProduction carProduction) throws EmailException;

    void advance() throws EmailException;

    void shutdown();

    void reactivate() throws EmailException;

    void startNext() throws EmailException;

    void transferWaitingList(CarAssemblyLine carAssemblyLine);

    List<CarProduction> getWaitingList();
}
