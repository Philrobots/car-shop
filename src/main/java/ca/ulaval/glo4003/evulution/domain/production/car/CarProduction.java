package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

import java.util.List;

public interface CarProduction {
    boolean isAssociatedWithManufacture();
    void associateWithManufacture(ProductionId productionId, Email email);

    void newCarCommand(CarAssemblyAdapter carAssemblyAdapter);

    void sendEmail(EmailFactory emailFactory) throws EmailException;

    boolean advance(CarAssemblyAdapter carAssemblyAdapter);

    ProductionId getProductionId();

    String getCarStyle();
}
