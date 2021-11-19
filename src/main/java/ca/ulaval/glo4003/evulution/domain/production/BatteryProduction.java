package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryProduction {
    private TransactionId transactionId;
    private String batteryType;
    private String email;

    public BatteryProduction(TransactionId transactionId, String batteryType, String email) {
        this.transactionId = transactionId;
        this.batteryType = batteryType;
        this.email = email;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public String getEmail() {
        return email;
    }
}
