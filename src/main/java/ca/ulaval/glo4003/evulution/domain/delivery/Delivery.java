package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Delivery {
    private final AccountId accountId;
    private final Integer assemblyTimeInWeeks;
    private final DeliveryId deliveryId;
    private final ArrayList<DeliveryStatus> status = new ArrayList<>();
    private int carTimeToProduce;
    private int batteryTimeToProduce;
    private DeliveryDetails deliveryDetails;
    private LocalDate deliveryDate;

    public Delivery(AccountId accountId, DeliveryId deliveryId, Integer assemblyTimeInWeeks) {
        this.accountId = accountId;
        this.deliveryId = deliveryId;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        this.status.add(DeliveryStatus.CREATED);
    }

    public void setStatus(DeliveryStatus status) throws DeliveryIncompleteException {
        switch (status) {
        case SHIPPED:
            // TODO Ask client about potential exception if delivery not confirmed
            break;
        case COMPLETED:
            if (!this.status.contains(DeliveryStatus.CONFIRMED) || !this.status.contains(DeliveryStatus.SHIPPED)) {
                throw new DeliveryIncompleteException();
            }
        }
        this.status.add(status);
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) throws DeliveryIncompleteException {
        this.deliveryDetails = deliveryDetails;
        setStatus(DeliveryStatus.CONFIRMED);
    }

    public LocalDate addDelayInWeeks(Integer weeks) {
        this.deliveryDate = this.deliveryDate.plusWeeks(weeks);
        return this.deliveryDate;
    }

    public void verifyAccountId(AccountId accountId) throws MismatchAccountIdWithDeliveryException {
        if (!this.accountId.equals(accountId))
            throw new MismatchAccountIdWithDeliveryException();
    }

    public void setCarTimeToProduce(int timeToProduceAsInt) {
        this.carTimeToProduce = timeToProduceAsInt;
        calculateDeliveryDate();
    }

    public void setBatteryTimeToProduce(int timeToProduceAsInt) {
        this.batteryTimeToProduce = timeToProduceAsInt;
        calculateDeliveryDate();
    }

    private void calculateDeliveryDate() {
        int expectedProductionTimeInWeeks = carTimeToProduce + batteryTimeToProduce + this.assemblyTimeInWeeks;
        this.deliveryDate = LocalDate.now().plusWeeks(expectedProductionTimeInWeeks);
    }

    public void completeDelivery() throws DeliveryIncompleteException {
        setStatus(DeliveryStatus.COMPLETED);
    }
}
