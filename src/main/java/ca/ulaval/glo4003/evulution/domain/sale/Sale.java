package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;

public class Sale {

    private String email;
    private TransactionId transactionId;
    private Car car;
    private Battery battery;
    private Delivery delivery;

    public Sale(String email, TransactionId transactionId, Delivery delivery) {
        this.email = email;
        this.transactionId = transactionId;
        this.delivery = delivery;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public String getEmail() {
        return email;
    }

    public void chooseCar(Car car) {
        this.car = car;
    }

    public void chooseBattery(Battery battery) {
        this.battery = battery;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public DeliveryId getDeliveryId() {
        return this.delivery.getDeliveryId();
    }

    public void chooseDelivery(String mode, String location) {
        this.delivery.chooseDeliveryLocation(mode, location);
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }
}
