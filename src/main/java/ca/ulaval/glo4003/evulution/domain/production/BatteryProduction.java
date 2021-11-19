package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryProduction {
    private TransactionId transactionId;
    private String batteryType;
    private String email;
    private Integer productionTimeInWeeks;

    public BatteryProduction(TransactionId transactionId, String batteryType, String email,
            Integer productionTimeInWeeks) {
        this.transactionId = transactionId;
        this.batteryType = batteryType;
        this.email = email;
        this.productionTimeInWeeks = productionTimeInWeeks;
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

    public Integer getProductionTimeInWeeks() {
        return productionTimeInWeeks;
    }
}
