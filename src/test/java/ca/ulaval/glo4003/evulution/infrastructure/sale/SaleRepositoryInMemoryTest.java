package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SaleRepositoryInMemoryTest {
    private static final SaleStatus PAID_STATUS = SaleStatus.PAID;

    private SaleRepositoryInMemory saleRepositoryInMemory;

    @Mock
    private SaleId saleId;

    @Mock
    private Sale sale;

    @Mock
    private AccountId accountId;

    @BeforeEach
    public void setUp() {
        this.saleRepositoryInMemory = new SaleRepositoryInMemory();
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

    @Test
    public void givenASale_whenGetSale_thenSaleIsReturned() throws SaleNotFoundException {
        // given
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);
        this.saleRepositoryInMemory.registerSale(sale);

        // when
        Sale currentSale = saleRepositoryInMemory.getSale(saleId);

        // then
        Assertions.assertEquals(sale, currentSale);
    }

    @Test
    public void givenASale_whenGetAccountIdFromSaleId_thenReturnsAccountId() throws SaleNotFoundException {
        // given
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);
        BDDMockito.given(sale.getAccountId()).willReturn(accountId);
        this.saleRepositoryInMemory.registerSale(sale);

        // when
        AccountId currentAccountId = this.saleRepositoryInMemory.getAccountIdFromSaleId(saleId);

        // then
        Assertions.assertEquals(accountId, currentAccountId);
    }

    @Test
    public void givenASale_whenSetStatus_thenSaleSetsStatus() throws SaleNotFoundException {
        // given
        BDDMockito.given(sale.getSaleId()).willReturn(saleId);
        this.saleRepositoryInMemory.registerSale(sale);

        // when
        this.saleRepositoryInMemory.setStatus(saleId, PAID_STATUS);

        // then
        Mockito.verify(sale).setStatus(PAID_STATUS);
    }
}
