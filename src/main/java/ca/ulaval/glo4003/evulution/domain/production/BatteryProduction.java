package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryProduction {
    private TransactionId transactionId;
    private String batteryType;

    public BatteryProduction(TransactionId transactionId, String batteryType) {
        this.transactionId = transactionId;
        this.batteryType = batteryType;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }
}
