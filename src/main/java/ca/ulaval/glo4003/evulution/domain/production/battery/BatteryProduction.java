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

    // public void sendEmail(EmailFactory emailFactory) throws EmailException {
    // emailFactory.createBatteryInProductionEmail(List.of(email), productionTimeInWeeks).send();
    // }

    public void newBatteryCommand(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.newBatteryCommand(productionId, batteryType);
    }

    public boolean advance(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.advance();
        return batteryAssemblyLineAdapter.isAssembled(productionId);
    }

    public Integer getProductionTimeInWeeks() {
        return productionTimeInWeeks;
    }
}
