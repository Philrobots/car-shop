package ca.ulaval.glo4003.evulution.domain.invoice.payments;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Biweekly implements PaymentStrategy {
    private final BigDecimal amountPerPayment;
    private Integer frequency = 2;
    private Integer paymentsLeft = (52 * 6) / frequency;
    private BigDecimal balance;

    public Biweekly(BigDecimal balance) {
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
        if (frequency == 2) {
            balance = balance.subtract(amountPerPayment);
            paymentsLeft--;
            frequency = 1;
        } else {
            frequency++;
        }
    }
}
