package ca.ulaval.glo4003.evulution.api.sale.dto;

public class SaleCreatedDto {
    public Integer transactionId;
    public String deliveryId;

    public SaleCreatedDto(Integer transactionId, String deliveryId) {
        this.transactionId = transactionId;
        this.deliveryId = deliveryId;
    }
}
