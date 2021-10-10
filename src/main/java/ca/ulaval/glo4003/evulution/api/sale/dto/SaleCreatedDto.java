package ca.ulaval.glo4003.evulution.api.sale.dto;

public class SaleCreatedDto {
    public String transaction_id;
    public String delivery_id;

    public SaleCreatedDto(String transaction_id, String delivery_id) {
        this.transaction_id = transaction_id;
        this.delivery_id = delivery_id;
    }
}
