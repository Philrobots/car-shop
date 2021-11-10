package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetails;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryStatus;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleNotCompletedException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sale {
    private final String email;
    private final TransactionId transactionId;
    private final Delivery delivery;
    private Car car;
    private Battery battery;
    private SaleStatus status;

    public Sale(String email, TransactionId transactionId, Delivery delivery) {
        this.email = email;
        this.transactionId = transactionId;
        this.delivery = delivery;
        this.status = SaleStatus.CREATED;
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

    public String getCarName() {
        return car.getName();
    }

    public String getBatteryType() {
        return battery.getType();
    }

    public Battery getBattery() {
        return this.battery;
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }

    public BigDecimal getPrice() {
        return this.car.getBasePrice().add(this.battery.getPrice());
    }

    public void setStatus(SaleStatus saleStatus) {
        this.status = saleStatus;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        if (this.status != SaleStatus.COMPLETED)
            throw new SaleNotCompletedException();
        this.delivery.setDeliveryDetails(deliveryDetails);
        setDeliveryStatus(DeliveryStatus.CONFIRMED);
    }

    public void chooseCar(Car car) {
        if (this.status == SaleStatus.COMPLETED)
            throw new SaleCompleteException();
        this.car = car;
    }

    public void chooseBattery(Battery battery) {
        if (car == null)
            throw new CarNotChosenBeforeBatteryException();
        if (this.status == SaleStatus.COMPLETED)
            throw new SaleCompleteException();
        this.battery = battery;
    }

    public void completeSale() {
        if (car == null || battery == null)
            throw new MissingElementsForSaleException();
        else if (this.status == SaleStatus.COMPLETED) {
            throw new SaleCompleteException();
        }

        this.status = SaleStatus.COMPLETED;
        this.delivery.calculateDeliveryDate(this.car.getTimeToProduceAsInt(), this.battery.getTimeToProduceAsInt());
    }

    public LocalDate addDelayInWeeks(int weeks) {
        return this.delivery.addDelayInWeeks(weeks);
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.delivery.setStatus(deliveryStatus);
    }
}
