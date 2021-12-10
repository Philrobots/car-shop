package ca.ulaval.glo4003.evulution.infrastructure.manufacture;

import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufactureDaoTest {

    @Mock
    private SaleId saleId;

    @Mock
    private Manufacture manufacture;

    private ManufactureDao manufactureDao;

    @BeforeEach
    void setUp() {
        manufactureDao = new ManufactureDao();
    }

    @Test
    public void givenNewManufactures_thenIsEmpty() {
        // then
        Assertions.assertTrue(manufactureDao.getManufactures().isEmpty());
    }

    @Test
    public void whenAdd_thenManufactureIsNotEmpty() {
        // when
        manufactureDao.add(saleId, manufacture);

        // then
        Assertions.assertFalse(manufactureDao.getManufactures().isEmpty());
    }

    @Test
    public void givenNewManufactures_whenGetManufactures_thenIsEmpty() {
        // then
        Assertions.assertTrue(manufactureDao.getManufactures().isEmpty());
    }

    @Test
    public void whenGetManufactures_thenManufactureIsNotEmpty() {
        // when
        manufactureDao.add(saleId, manufacture);

        // then
        Assertions.assertFalse(manufactureDao.getManufactures().isEmpty());
    }

    @Test
    public void givenNewManufactures_whenGetManufacturesReadyForProduction_thenReadyForProductionIsEmpty() {
        // then
        Assertions.assertTrue(manufactureDao.getManufacturesReadyForProduction().isEmpty());
    }

    @Test
    public void givenUnreadyManufacture_whenGetManufacturesReadyToProduce_thenManufactureReadyForProductionIsEmpty() {
        // given
        BDDMockito.given(manufacture.isReadyToProduce()).willReturn(false);

        // when
        manufactureDao.add(saleId, manufacture);

        // then
        Assertions.assertTrue(manufactureDao.getManufacturesReadyForProduction().isEmpty());
    }

    @Test
    public void whenGetManufacturesReadyToProduce_thenManufactureReadyForProductionIsNotEmpty() {
        // given
        BDDMockito.given(manufacture.isReadyToProduce()).willReturn(true);

        // when
        manufactureDao.add(saleId, manufacture);

        // then
        Assertions.assertFalse(manufactureDao.getManufacturesReadyForProduction().isEmpty());
    }

}
