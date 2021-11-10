package ca.ulaval.glo4003.evulution.domain.invoice.payments;

import java.math.BigDecimal;

public interface PaymentStrategy {
    void pay();

    Integer getPaymentsLeft();

    BigDecimal getAmountPerPayment();
}
