package ca.ulaval.glo4003.evulution.domain.production.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public class BatteryProduction {
    private ProductionId productionId;
    private String batteryType;
    private Integer productionTimeInWeeks;

    public BatteryProduction(ProductionId productionId, String batteryType, Integer productionTimeInWeeks) {
        this.productionId = productionId;
        this.batteryType = batteryType;
        this.productionTimeInWeeks = productionTimeInWeeks;
    }

    public ProductionId getProductionId() {
        return productionId;
    }


    public void newBatteryCommand(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.newBatteryCommand(this.productionId, this.batteryType);
    }

    public boolean advance(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.advance();
        return batteryAssemblyLineAdapter.isAssembled(this.productionId);
    }

    public Integer getProductionTimeInWeeks() {
        return this.productionTimeInWeeks;
    }
}
