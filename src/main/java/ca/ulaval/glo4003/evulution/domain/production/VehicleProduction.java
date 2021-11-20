package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class VehicleProduction {
    private TransactionId transactionId;
    private String name;
    private String email;
    private Integer productionTimeInWeeks;
    private boolean emailSent = false;

    public VehicleProduction(TransactionId transactionId, String name, String email, Integer productionTimeInWeeks) {
        this.transactionId = transactionId;
        this.name = name;
        this.email = email;
        this.productionTimeInWeeks = productionTimeInWeeks;
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

    public Integer getProductionTimeInWeeks() {
        return productionTimeInWeeks;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public boolean isEmailSent() {
        return emailSent;
    }
}
