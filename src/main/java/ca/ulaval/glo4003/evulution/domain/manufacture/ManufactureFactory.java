package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;

public class ManufactureFactory {
    private final DeliveryFactory deliveryFactory;

    public ManufactureFactory(DeliveryFactory deliveryFactory) {
        this.deliveryFactory = deliveryFactory;
    }

    public Manufacture create(AccountId accountId) {
        Delivery delivery = this.deliveryFactory.create(accountId);
        ProductionId productionId = new ProductionId();
        return new Manufacture(productionId, delivery);
    }
}
