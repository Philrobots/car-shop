package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.exception.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exception.SaleNotCompletedException;

public class Sale {

    private String email;
    private TransactionId transactionId;
    private Car car;
    private Battery battery;
    private Delivery delivery;
    private Boolean isSaleCompleted = false;

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

    public Delivery getDelivery() {
        return delivery;
    }

    public DeliveryId getDeliveryId() {
        return this.delivery.getDeliveryId();
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
        isSaleCompleted = true;
    }

    public void chooseDelivery(String mode, String location) {
        if (!isSaleCompleted)
            throw new SaleNotCompletedException();
        this.delivery.chooseDeliveryLocation(mode, location);
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }
}
