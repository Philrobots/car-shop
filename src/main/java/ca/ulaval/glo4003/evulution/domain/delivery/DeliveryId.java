package ca.ulaval.glo4003.evulution.domain.delivery;

import java.util.Objects;

public class DeliveryId {
    private final Integer deliveryId;

    public DeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getId() {
        return this.deliveryId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DeliveryId that = (DeliveryId) o;
        return Objects.equals(deliveryId, that.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId);
    }
}
