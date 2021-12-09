package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public interface CarAssemblyLine {
    void addProduction(CarProduction carProduction) throws EmailException;

    void advance() throws EmailException;

    void shutdown();

    void reactivate() throws EmailException;

    void startNext() throws EmailException;
}
