package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public class CompleteAssemblyProductionFactory {
    public CompleteAssemblyProduction create(ProductionId productionId, Delivery delivery, String email) {
        return new CompleteAssemblyProduction(productionId, delivery, email);
    }
}
