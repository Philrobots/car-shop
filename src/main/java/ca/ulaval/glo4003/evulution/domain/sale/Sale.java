package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleCompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleNotCompletedException;

import java.time.LocalDate;

public class Sale {
    private final String email;
    private final TransactionId transactionId;
    private final DeliveryId deliveryId;
    private final Integer assemblyTimeInWeeks;
    private Delivery delivery;
    private Car car;
    private Battery battery;
    private Boolean isSaleCompleted = false;
    private LocalDate expectedDeliveryDate;

    public Sale(String email, Integer assemblyTimeInWeeks, TransactionId transactionId, DeliveryId deliveryId) {
        this.email = email;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
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
        int expectedProductionTimeInWeeks = this.car.getTimeToProduceAsInt() + this.battery.getTimeToProduceAsInt()
                + this.assemblyTimeInWeeks;
        this.expectedDeliveryDate = LocalDate.now().plusWeeks(expectedProductionTimeInWeeks);
    }

    public void chooseDelivery(Delivery delivery) {
        if (!isSaleCompleted)
            throw new SaleNotCompletedException();
        this.delivery = delivery;
    }

    public Integer getBatteryAutonomy() {
        return battery.calculateEstimatedRange(car.getEfficiencyEquivalenceRate());
    }

    public LocalDate addDelayInWeeks(int weeks) {
        this.expectedDeliveryDate = this.expectedDeliveryDate.plusWeeks(weeks);
        return this.expectedDeliveryDate;
    }
}
