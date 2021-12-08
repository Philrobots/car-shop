package ca.ulaval.glo4003.evulution.service.delivery.dto;

public class DeliveryCompleteDto {
    public Float paymentTaken;
    public Integer numberOfPaymentLeft;

    public DeliveryCompleteDto(Float paymentTaken, Integer numberOfPaymentLeft) {
        this.paymentTaken = paymentTaken;
        this.numberOfPaymentLeft = numberOfPaymentLeft;
    }
}
