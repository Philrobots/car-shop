package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;

public class ManufactureFactory {

    private DeliveryFactory deliveryFactory;

    public ManufactureFactory(DeliveryFactory deliveryFactory) {
        this.deliveryFactory = deliveryFactory;
    }

    public Manufacture create(AccountId accountId) throws DeliveryIncompleteException {
        Delivery delivery = deliveryFactory.create(accountId);
        return new Manufacture(delivery);
    }
}
