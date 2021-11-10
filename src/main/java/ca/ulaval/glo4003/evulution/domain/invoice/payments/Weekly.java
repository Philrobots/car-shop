package ca.ulaval.glo4003.evulution.domain.invoice.payments;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Weekly implements PaymentStrategy {
    private final BigDecimal amountPerPayment;
    private Integer paymentsLeft = (52 * 6);
    private BigDecimal balance;

    public Weekly(BigDecimal balance) {
        this.balance = balance;
        this.amountPerPayment = balance.divide(BigDecimal.valueOf(this.paymentsLeft), 2, RoundingMode.HALF_EVEN);
    }

    public Integer getPaymentsLeft() {
        return this.paymentsLeft;
    }

    public BigDecimal getAmountPerPayment() {
        return this.amountPerPayment;
    }

    @Override
    public void pay() {
        balance = balance.subtract(amountPerPayment);
        paymentsLeft--;
    }
}
