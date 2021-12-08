package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryStatus;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

import java.time.LocalDate;
import java.util.List;

public class CompleteAssemblyProduction {
    private Delivery delivery;
    private String email;
    private ProductionId productionId;

    public CompleteAssemblyProduction(ProductionId productionId, Delivery delivery, String email) {
        this.productionId = productionId;
        this.delivery = delivery;
        this.email = email;
    }

    public void sendProductionStartEmail(EmailFactory emailFactory, int weeksRemaining) throws EmailException {
        emailFactory.createAssemblyInProductionEmail(List.of(email), weeksRemaining).send();
    }

    public void ship() throws DeliveryIncompleteException {
        delivery.setStatus(DeliveryStatus.SHIPPED);
    }

    public ProductionId getProductionId() {
        return productionId;
    }

    public void addDelayInWeeksAndSendEmail(Integer assemblyDelayInWeeks, EmailFactory emailFactory) {
        LocalDate localDate = delivery.addDelayInWeeks(assemblyDelayInWeeks);
        emailFactory.createAssemblyDelayEmail(List.of(email), localDate);
    }
}
