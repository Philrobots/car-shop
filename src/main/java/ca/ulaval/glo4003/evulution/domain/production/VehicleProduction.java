package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class VehicleProduction {
    private TransactionId transactionId;
    private String name;
    private String email;

    public VehicleProduction(TransactionId transactionId, String name, String email) {
        this.transactionId = transactionId;
        this.name = name;
        this.email = email;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
