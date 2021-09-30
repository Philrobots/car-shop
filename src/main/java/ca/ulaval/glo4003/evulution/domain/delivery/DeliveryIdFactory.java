package ca.ulaval.glo4003.evulution.domain.delivery;

public class DeliveryIdFactory {

    private int deliveryCounter = 0;

    public DeliveryId create() {
        deliveryCounter++;
        return new DeliveryId(deliveryCounter);
    }

    public DeliveryId createFromInt(int deliveryId) {
        return new DeliveryId(deliveryId);
    }
}
