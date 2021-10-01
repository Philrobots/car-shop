package ca.ulaval.glo4003.evulution.api.sale.dto;

public class SaleCreatedDto {
    public Integer transaction_id;
    public String delivery_id;

    public SaleCreatedDto(Integer transaction_id, String delivery_id) {
        this.transaction_id = transaction_id;
        this.delivery_id = delivery_id;
    }
}
