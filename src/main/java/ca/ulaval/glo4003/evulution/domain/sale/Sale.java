package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetails;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleNotCompletedException;

import java.time.LocalDate;

public class Sale {
    private final String email;
    private final TransactionId transactionId;
    private final Delivery delivery;
    private Car car;
    private Battery battery;
    private Boolean isSaleCompleted = false;

    public Sale(String email, TransactionId transactionId, Delivery delivery) {
        this.email = email;
        this.transactionId = transactionId;
        this.delivery = delivery;
    }

    public String getEmail() {
        return email;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public DeliveryId getDeliveryId() {
        return delivery.getDeliveryId();
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Car getCar() {
        return this.car;
    }

    public String getCarName(){
        return car.getName();
    }

    public String getBatteryType(){
        return battery.getType();
    }

    public Battery getBattery() {
        return this.battery;
    }

    public void setSaleAsCompleted() {
        this.isSaleCompleted = true;
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
            throw new SaleCompleteException();
        }

        this.setSaleAsCompleted();
        this.delivery.calculateDeliveryDate(this.car.getTimeToProduceAsInt(), this.battery.getTimeToProduceAsInt());
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }

    public LocalDate addDelayInWeeks(int weeks) {
        return this.delivery.addDelayInWeeks(weeks);
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        if (!isSaleCompleted)
            throw new SaleNotCompletedException();
        this.delivery.setDeliveryDetails(deliveryDetails);
    }

    public void deliverToCampus() {
        delivery.deliverToCampus();
    }
}
