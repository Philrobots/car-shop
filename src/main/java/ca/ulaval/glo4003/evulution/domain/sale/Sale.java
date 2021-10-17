package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.*;

public class Sale {

    private String email;
    private TransactionId transactionId;
    private DeliveryId deliveryId;
    private Delivery delivery;
    private Car car;
    private Battery battery;
    private Boolean isSaleCompleted = false;

    public Sale(String email, TransactionId transactionId, DeliveryId deliveryId) {
        this.email = email;
        this.transactionId = transactionId;
        this.deliveryId = deliveryId;
    }

    public String getEmail() {
        return email;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Car getCar() {
        return this.car;
    }

    public Battery getBattery() {
        return this.battery;
    }

    public void chooseCar(Car car) {
        if (isSaleCompleted)
            throw new SaleCompleteException();
        this.car = car;
    }

    public void chooseBattery(Battery battery) {
        if (car == null)
            throw new CarNotChosenBeforeBatteryException();
        if (isSaleCompleted)
            throw new SaleCompleteException();
        this.battery = battery;
    }

    public void completeSale() {
        if (car == null || battery == null)
            throw new MissingElementsForSaleException();
        else if (isSaleCompleted) {
            throw new SaleAlreadyCompleteException();
        }
        isSaleCompleted = true;
    }

    public void chooseDelivery(Delivery delivery) {
        if (!isSaleCompleted)
            throw new SaleNotCompletedException();
        this.delivery = delivery;
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }
}
