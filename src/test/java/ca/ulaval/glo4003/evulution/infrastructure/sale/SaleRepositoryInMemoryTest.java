package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SaleRepositoryInMemoryTest {
    private SaleRepositoryInMemory saleRepositoryInMemory;

    @Mock
    private SaleId saleId;

    @Mock
    private DeliveryId deliveryId;

    @Mock
    private Sale sale;

    @BeforeEach
    public void setUp() {
        saleRepositoryInMemory = new SaleRepositoryInMemory();
    }

    @Test
    public void givenASale_whenRegisterSale_thenRepositoryInMemoryContainsSale() throws SaleNotFoundException {
        // given
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);

        // when
        this.saleRepositoryInMemory.registerSale(sale);

        // then
        assertEquals(sale, this.saleRepositoryInMemory.getSale(saleId));
    }

    @Test
    public void givenNoSale_whenGetSale_thenThrowsSaleNotFoundException() {
        // when
        Executable getSale = () -> this.saleRepositoryInMemory.getSale(saleId);

        // then
        assertThrows(SaleNotFoundException.class, getSale);
    }
}
