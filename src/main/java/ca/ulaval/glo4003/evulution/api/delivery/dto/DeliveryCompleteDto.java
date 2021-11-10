package ca.ulaval.glo4003.evulution.api.delivery.dto;

public class DeliveryCompleteDto {
    public Float payment_taken;
    public Integer number_of_payment_left;

    public DeliveryCompleteDto(Float payment_taken, Integer number_of_payment_left) {
        this.payment_taken = payment_taken;
        this.number_of_payment_left = number_of_payment_left;
    }
}
