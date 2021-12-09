package ca.ulaval.glo4003.evulution.domain.production.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.List;

public class BatteryProduction {
    private ProductionId productionId;
    private String batteryType;
    private String email;
    private Integer productionTimeInWeeks;

    public BatteryProduction(ProductionId productionId, String batteryType, String email,
            Integer productionTimeInWeeks) {
        this.productionId = productionId;
        this.batteryType = batteryType;
        this.email = email;
        this.productionTimeInWeeks = productionTimeInWeeks;
    }

    public ProductionId getProductionId() {
        return productionId;
    }

    public void sendEmail(EmailFactory emailFactory) throws EmailException {
        emailFactory.createBatteryBatteryInProductionEmail(List.of(email), productionTimeInWeeks).send();
    }

    public void newBatteryCommand(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.newBatteryCommand(productionId, batteryType);
    }

    public boolean advance(BatteryAssemblyAdapter batteryAssemblyLineAdapter) {
        batteryAssemblyLineAdapter.advance();
        return batteryAssemblyLineAdapter.isAssembled(productionId);
    }
}
