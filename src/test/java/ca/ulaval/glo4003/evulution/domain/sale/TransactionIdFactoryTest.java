package ca.ulaval.glo4003.evulution.domain.sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionIdFactoryTest {

    private TransactionIdFactory transactionIdFactory;

    @BeforeEach
    public void setUp() {
        transactionIdFactory = new TransactionIdFactory();
    }

    @Test
    public void whenCreate_thenATransactionIdIsCreated() {
        // when
        TransactionId transactionId = transactionIdFactory.create();

        // then
        assertNotNull(transactionId);
    }

    @Test
    public void givenTransactionId_whenCreateFromInt_thenATransactionIdIsCreated() {
        // given
        int deliveryIdInt = 1;

        // when
        TransactionId transactionId = transactionIdFactory.createFromInt(deliveryIdInt);

        // then
        assertNotNull(transactionId);
    }
}
