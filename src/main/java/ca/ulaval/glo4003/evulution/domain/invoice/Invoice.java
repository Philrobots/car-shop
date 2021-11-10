package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.payments.PaymentStrategy;

import java.math.BigDecimal;

public class Invoice {
    private final int bank_no;
    private final int account_no;
    private final Frequency frequency;
    private final PaymentStrategy paymentStrategy;

    public Invoice(int bank_no, int account_no, Frequency frequency, PaymentStrategy paymentStrategy) {
        this.bank_no = bank_no;
        this.account_no = account_no;
        this.frequency = frequency;
        this.paymentStrategy = paymentStrategy;
    }

    public BigDecimal getPaymentsTaken() {
        return this.paymentStrategy.getAmountPerPayment();
    }

    public Integer getPaymentsLeft() {
        return this.paymentStrategy.getPaymentsLeft();
    }

    public void pay() {
        this.paymentStrategy.pay();
    }

}
