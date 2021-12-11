package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public class CarProductionWithoutManufacture implements CarProduction {
    private final ProductionId productionId;
    private final String carStyle;
    private final int carProductionTimeInWeeks;

    public CarProductionWithoutManufacture(ProductionId productionId, String carStyle, int carProductionTime) {
        this.productionId = productionId;
        this.carStyle = carStyle;
        this.carProductionTimeInWeeks = carProductionTime;
    }

    @Override
    public boolean isAssociatedWithManufacture() {
        return false;
    }

    @Override
    public void newCarCommand(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.newVehicleCommand(this.productionId, this.carStyle);
    }

    @Override
    public boolean advance(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.advance();
        return carAssemblyAdapter.isAssembled(this.productionId);
    }

    @Override
    public ProductionId getProductionId() {
        return this.productionId;
    }

    @Override
    public String getCarStyle() {
        return this.carStyle;
    }

    @Override
    public Integer getTimeToProduce() {
        return carProductionTimeInWeeks;
    }
}
