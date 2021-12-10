package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.Email;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.List;

public class CarProductionAssociatedWithManufacture implements CarProduction {

    private ProductionId productionId;
    private int carProductionTimeInWeeks;
    private String carStyle;

    public CarProductionAssociatedWithManufacture(ProductionId productionId, String carStyle, int carProductionTime) {
        this.productionId = productionId;
        this.carStyle = carStyle;
        this.carProductionTimeInWeeks = carProductionTime;
    }

    public boolean isAssociatedWithManufacture() {
        return true;
    }

    public void newCarCommand(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.newVehicleCommand(productionId, carStyle);
    }

    public boolean advance(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.advance();
        return carAssemblyAdapter.isAssembled(productionId);
    }

    public ProductionId getProductionId() {
        return productionId;
    }

    public String getCarStyle() {
        return carStyle;
    }

    public Integer getTimeToProduce() {
        return carProductionTimeInWeeks;
    }
}
