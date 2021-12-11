package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

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
        carAssemblyAdapter.newVehicleCommand(this.productionId, this.carStyle);
    }

    public boolean advance(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.advance();
        return carAssemblyAdapter.isAssembled(this.productionId);
    }

    public ProductionId getProductionId() {
        return this.productionId;
    }

    public String getCarStyle() {
        return this.carStyle;
    }

    public Integer getTimeToProduce() {
        return this.carProductionTimeInWeeks;
    }
}
