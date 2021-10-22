package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class VehicleProduction {
    private TransactionId transactionId;
    private String name;

    public VehicleProduction(TransactionId transactionId, String name) {
        this.transactionId = transactionId;
        this.name = name;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public String getName() {
        return name;
    }
}
