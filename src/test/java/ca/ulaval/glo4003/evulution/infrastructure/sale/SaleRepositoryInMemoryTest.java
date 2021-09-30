package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SaleRepositoryInMemoryTest {

    private static final TransactionId A_TRANSACTION_ID = new TransactionId(1234);
    private static final String AN_EMAIL = "rob@live.com";

    private SaleRepositoryInMemory saleRepositoryInMemory;

    @Mock
    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        saleRepositoryInMemory = new SaleRepositoryInMemory();
    }

    @Test
    public void givenASale_whenRegisterSale_thenRepositoryInMemoryContainsSale() {

        // given
        Sale expectedSale = new Sale(AN_EMAIL, A_TRANSACTION_ID, delivery);

        // when
        this.saleRepositoryInMemory.registerSale(expectedSale);

        // then
        assertEquals(expectedSale, this.saleRepositoryInMemory.getSale(A_TRANSACTION_ID));
    }

    @Test
    public void givenNoSale_whenGetSale_thenRepositoryDoesNotContainSale() {
        // then
        assertNull(this.saleRepositoryInMemory.getSale(A_TRANSACTION_ID));
    }

}
