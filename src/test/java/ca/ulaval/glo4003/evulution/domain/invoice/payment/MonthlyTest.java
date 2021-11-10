package ca.ulaval.glo4003.evulution.domain.invoice.payment;

import ca.ulaval.glo4003.evulution.domain.invoice.payments.Monthly;
import ca.ulaval.glo4003.evulution.domain.invoice.payments.PaymentStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MonthlyTest {

    private static final BigDecimal MONTHLY = BigDecimal.valueOf(4);
    private static final BigDecimal A_BALANCE = BigDecimal.valueOf(10000);
    private static final BigDecimal YEARS = BigDecimal.valueOf(6);
    private static final BigDecimal MONTHLY_TOTAL_PAYMENTS = BigDecimal.valueOf(52)
            .divide(MONTHLY, 2, RoundingMode.HALF_EVEN).multiply(YEARS);

    private PaymentStrategy paymentStrategy;

    @BeforeEach
    void setUp() {
        this.paymentStrategy = new Monthly(A_BALANCE);
    }

    @Test
    public void whenGetAmountPerPayment_thenReturnsAppropriateAmount() {
        // when
        BigDecimal actualAmount = paymentStrategy.getAmountPerPayment();

        // then
        BigDecimal expectedAmount = A_BALANCE.divide(MONTHLY_TOTAL_PAYMENTS, 2, RoundingMode.HALF_EVEN);

        Assertions.assertEquals(actualAmount, expectedAmount);
    }

    @Test
    public void givenNewInvoice_whenGetPaymentsLeft_thenReturnsAppropriateAmount() {
        // when
        Integer actualAmount = paymentStrategy.getPaymentsLeft();

        // then
        Assertions.assertEquals(actualAmount, MONTHLY_TOTAL_PAYMENTS.intValueExact());
    }

    @Test
    public void givenNewInvoice_whenPay_thenPaymentsLeftDecrements() {
        // when
        paymentStrategy.pay();

        // then
        Integer expectedPaymentsLeft = MONTHLY_TOTAL_PAYMENTS.subtract(BigDecimal.valueOf(1)).intValueExact();
        Assertions.assertEquals(paymentStrategy.getPaymentsLeft(), expectedPaymentsLeft);
    }

    @Test
    public void givenLastWeeksPaidInvoice_whenPay_thenPaymentsLeftStaysTheSame() {
        // given
        paymentStrategy.pay();

        // when
        paymentStrategy.pay();

        // then
        Integer expectedPaymentsLeft = MONTHLY_TOTAL_PAYMENTS.subtract(BigDecimal.valueOf(1)).intValueExact();
        Assertions.assertEquals(paymentStrategy.getPaymentsLeft(), expectedPaymentsLeft);
    }

    @Test
    public void givenInvoicePaidTwoWeeksAgo_whenPay_thenPaymentsLeftStaysTheSame() {
        // given
        paymentStrategy.pay();
        paymentStrategy.pay();

        // when
        paymentStrategy.pay();

        // then
        Integer expectedPaymentsLeft = MONTHLY_TOTAL_PAYMENTS.subtract(BigDecimal.valueOf(1)).intValueExact();
        Assertions.assertEquals(paymentStrategy.getPaymentsLeft(), expectedPaymentsLeft);
    }

    @Test
    public void givenInvoicePaidThreeWeeksAgo_whenPay_thenPaymentsLeftStaysTheSame() {
        // given
        paymentStrategy.pay();
        paymentStrategy.pay();
        paymentStrategy.pay();

        // when
        paymentStrategy.pay();

        // then
        Integer expectedPaymentsLeft = MONTHLY_TOTAL_PAYMENTS.subtract(BigDecimal.valueOf(1)).intValueExact();
        Assertions.assertEquals(paymentStrategy.getPaymentsLeft(), expectedPaymentsLeft);
    }
}
