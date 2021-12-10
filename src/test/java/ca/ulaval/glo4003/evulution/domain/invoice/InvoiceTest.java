package ca.ulaval.glo4003.evulution.domain.invoice;

import ca.ulaval.glo4003.evulution.domain.invoice.payments.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvoiceTest {

    private static final Integer A_BANK_NO = 1;
    private static final Integer AN_ACCOUNT_NO = 1;

    private final Frequency frequency = Frequency.BIWEEKLY;

    @Mock
    private PaymentStrategy paymentStrategy;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        this.invoice = new Invoice(A_BANK_NO, AN_ACCOUNT_NO, frequency, paymentStrategy);
    }

    @Test
    public void whenGetPaymentsTaken_thenPaymentStrategyGetsAmountPerPayment() {
        // when
        this.invoice.getPaymentsTaken();

        // then
        Mockito.verify(paymentStrategy).getAmountPerPayment();
    }

    @Test
    public void whenGetPaymentsLeft_thenPaymentStrategyGetsPaymentLeft() {
        // when
        this.invoice.getPaymentsLeft();

        // then
        Mockito.verify(paymentStrategy).getPaymentsLeft();

    }

    @Test
    public void whenPay_thenPaymentStrategyPays() {
        // when
        this.invoice.pay();

        // then
        Mockito.verify(paymentStrategy).pay();
    }

}
