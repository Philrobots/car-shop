package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundFromDeliveryIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SaleRepositoryInMemoryTest {
    private SaleRepositoryInMemory saleRepositoryInMemory;

    @Mock
    private TransactionId transactionId;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private Sale sale;

    @BeforeEach
    public void setUp() {
        saleRepositoryInMemory = new SaleRepositoryInMemory();
    }

    @Test
    public void givenASale_whenRegisterSale_thenRepositoryInMemoryContainsSale() {
        // given
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);

        // when
        this.saleRepositoryInMemory.registerSale(sale);

        // then
        assertEquals(sale, this.saleRepositoryInMemory.getSale(transactionId));
    }

    @Test
    public void givenNoSale_whenGetSale_thenRepositoryDoesNotContainSale() {
        // then
        assertNull(this.saleRepositoryInMemory.getSale(transactionId));
    }

    @Test
    public void givenSale_whenGetSaleFromDeliveryId_thenSaleIsReturn() {
        // given
        BDDMockito.given(sale.getTransactionId()).willReturn(transactionId);
        BDDMockito.given(sale.getDeliveryId()).willReturn(deliveryId);
        this.saleRepositoryInMemory.registerSale(sale);

        // when
        Sale inMemorySale = saleRepositoryInMemory.getSaleFromDeliveryId(deliveryId);

        // then
        assertEquals(sale, inMemorySale);
    }

    @Test
    public void givenNoSale_whenGetSaleFromDeliveryId_thenThrowSaleNotFoundFromDeliveryIdException() {
        // when
        Executable getSaleFromDeliveryId = () -> saleRepositoryInMemory.getSaleFromDeliveryId(deliveryId);

        // then
        assertThrows(SaleNotFoundFromDeliveryIdException.class, getSaleFromDeliveryId);
    }
}
