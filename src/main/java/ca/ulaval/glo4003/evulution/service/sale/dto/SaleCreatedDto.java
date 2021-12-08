package ca.ulaval.glo4003.evulution.service.sale.dto;

public class SaleCreatedDto {
    public String saleId;
    public String deliveryId;

    public SaleCreatedDto(String saleId, String deliveryId) {
        this.saleId = saleId;
        this.deliveryId = deliveryId;
    }
}
