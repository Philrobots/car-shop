package ca.ulaval.glo4003.evulution.domain.production.complete;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProduction;

public class CompleteAssemblyProductionFactory {
    public CompleteAssemblyProduction create(ProductionId productionId, Delivery delivery) {
        return new CompleteAssemblyProduction(productionId, delivery);
    }
}
