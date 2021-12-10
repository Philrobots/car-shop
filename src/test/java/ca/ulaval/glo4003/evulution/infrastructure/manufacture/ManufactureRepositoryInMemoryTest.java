package ca.ulaval.glo4003.evulution.infrastructure.manufacture;

import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufactureRepositoryInMemoryTest {

    @Mock
    private ManufactureDao manufactureDao;

    @Mock
    private SaleId saleId;

    @Mock
    private Manufacture manufacture;

    private ManufactureRepositoryInMemory manufactureRepositoryInMemory;

    @BeforeEach
    void setUp() {
        this.manufactureRepositoryInMemory = new ManufactureRepositoryInMemory(manufactureDao);
    }

    @Test
    public void whenAddManufacture_thenManufactureDaoAdds() {
        // when
        this.manufactureRepositoryInMemory.addManufacture(saleId, manufacture);

        // then
        Mockito.verify(manufactureDao).add(saleId, manufacture);
    }

    @Test
    public void whenGetBySaleId_thenManufactureDaoGetsBySaleId() {
        // when
        this.manufactureRepositoryInMemory.getBySaleId(saleId);

        // then
        Mockito.verify(manufactureDao).getBySaleId(saleId);
    }

    @Test
    public void whenUpdateManufacture_thenManufactureDaoAddsManufacture() {
        // when
        this.manufactureRepositoryInMemory.updateManufacture(saleId, manufacture);

        // then
        Mockito.verify(manufactureDao).add(saleId, manufacture);
    }

    @Test
    public void whenGetManufactureReadyForProduction_thenManufactureDaoGetsManufactureReadyForProduction() {
        // when
        this.manufactureRepositoryInMemory.getManufacturesReadyForProduction();

        // then
        Mockito.verify(manufactureDao).getManufacturesReadyForProduction();
    }
}
