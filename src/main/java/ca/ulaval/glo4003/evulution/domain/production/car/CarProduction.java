package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public interface CarProduction {
    boolean isAssociatedWithManufacture();

    void newCarCommand(CarAssemblyAdapter carAssemblyAdapter);

    boolean advance(CarAssemblyAdapter carAssemblyAdapter);

    ProductionId getProductionId();

    String getCarStyle();

    Integer getTimeToProduce();
}
