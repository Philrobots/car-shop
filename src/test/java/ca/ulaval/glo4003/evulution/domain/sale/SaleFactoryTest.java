package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SaleFactoryTest {
    private static final String AN_EMAIL = "jo@live.com";

    private SaleFactory saleFactory;

    @Mock
    private TransactionIdFactory transactionIdFactory;

    @Mock
    private DeliveryFactory deliveryFactory;

    @BeforeEach
    public void setUp() {
        saleFactory = new SaleFactory(transactionIdFactory, deliveryFactory);
    }

    @Test
    public void whenCreate_thenSaleIsCreated() {
        // when
        Sale sale = saleFactory.create(AN_EMAIL);

        // then
        assertNotNull(sale);
    }

    @Test
    public void whenCreate_thenTransactionIdFactoryIsCalled() {
        // when
        saleFactory.create(AN_EMAIL);

        // then
        Mockito.verify(transactionIdFactory).create();
    }

    @Test
    public void whenCreate_thenDeliveryIdFactoryIsCalled() {
        // when
        saleFactory.create(AN_EMAIL);

        // then
        Mockito.verify(deliveryFactory).create();
    }
}
