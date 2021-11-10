package ca.ulaval.glo4003.evulution.domain.sale;

public enum SaleStatus {
    CREATED("created"), COMPLETED("completed"), PAID("paid");

    private final String status;

    SaleStatus(String saleStatus) {
        this.status = saleStatus;
    }
}
