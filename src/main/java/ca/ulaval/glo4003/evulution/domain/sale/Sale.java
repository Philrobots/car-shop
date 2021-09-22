package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.Battery;

public class Sale {

    private String email;
    private TransactionId transactionId;
    private Car car;
    private Battery battery;

    public Sale(String email, TransactionId transactionId) {
        this.email = email;
        this.transactionId = transactionId;
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
}
