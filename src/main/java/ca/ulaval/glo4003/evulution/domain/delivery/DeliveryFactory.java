package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;

public class DeliveryFactory {
    private Integer assemblyTimeInWeeks;
    private DeliveryIdFactory deliveryIdFactory;

    public DeliveryFactory(Integer assemblyTimeInWeeks, DeliveryIdFactory deliveryIdFactory) {
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public Delivery create(AccountId accountId) {
        return new Delivery(accountId, deliveryIdFactory.create(), assemblyTimeInWeeks);
    }
}
