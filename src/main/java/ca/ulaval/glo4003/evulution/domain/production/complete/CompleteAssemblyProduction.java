package ca.ulaval.glo4003.evulution.domain.production.complete;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryStatus;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.time.LocalDate;
import java.util.List;

public class CompleteAssemblyProduction {
    private Delivery delivery;
    private ProductionId productionId;

    public CompleteAssemblyProduction(ProductionId productionId, Delivery delivery) {
        this.productionId = productionId;
        this.delivery = delivery;
    }

    public void ship() throws DeliveryIncompleteException {
        delivery.setStatus(DeliveryStatus.SHIPPED);
    }

    public ProductionId getProductionId() {
        return productionId;
    }

    public LocalDate addDelayInWeeksAndSendEmail(Integer assemblyDelayInWeeks)
            throws EmailException {
        return delivery.addDelayInWeeks(assemblyDelayInWeeks);
    }
}
