package ca.ulaval.glo4003.evulution.infrastructure.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.manufacture.ManufactureDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeliveryRepositoryInMemoryTest {
    private static final DeliveryId A_VALID_DELIVERY_ID = new DeliveryId(564);
    private static final DeliveryId AN_INVALID_DELIVERY_ID = new DeliveryId(854);
    private static final SaleId A_SALE_ID = new SaleId(246);
    private static final int ASSEMBLY_TIME_IN_WEEK = 2;

    private DeliveryRepositoryInMemory deliveryRepositoryInMemory;

    private Manufacture manufacture;

    private Delivery delivery;

    @Mock
    AccountId accountId;

    @Mock
    ManufactureDao manufactureDao;

    @BeforeEach
    public void setUp() {
        this.deliveryRepositoryInMemory = new DeliveryRepositoryInMemory(manufactureDao);
        this.delivery = new Delivery(accountId, A_VALID_DELIVERY_ID, ASSEMBLY_TIME_IN_WEEK);
        this.manufacture = new Manufacture(delivery);
    }

    @Test
    public void givenAValidDeliveryId_whenGetDelivery_thenReturnsDelivery() throws DeliveryNotFoundException {
        // given
        BDDMockito.given(manufactureDao.getManufactures()).willReturn(List.of(manufacture));

        // when
        Delivery deliveryReturned = deliveryRepositoryInMemory.getDelivery(A_VALID_DELIVERY_ID);

        // then
        assertEquals(delivery, deliveryReturned);
    }

    @Test
    public void givenAnInvalidDeliveryId_whenGetDelivery_thenThrowsDeliveryNotFoundException() {
        // given
        BDDMockito.given(manufactureDao.getManufactures()).willReturn(List.of(manufacture));

        // when
        Executable getManufactures = () -> deliveryRepositoryInMemory.getDelivery(AN_INVALID_DELIVERY_ID);

        // then
        assertThrows(DeliveryNotFoundException.class, getManufactures);
    }

    @Test
    public void givenAValidDeliveryId_whenGetSaleId_thenReturnsSaleId() throws DeliveryNotFoundException {
        // given
        BDDMockito.given(manufactureDao.getManufacturesMapEntries())
                .willReturn(Map.of(A_SALE_ID, manufacture).entrySet());

        // when
        SaleId saleId = deliveryRepositoryInMemory.getSaleId(A_VALID_DELIVERY_ID);

        // then
        assertEquals(A_SALE_ID, saleId);
    }

    @Test
    public void givenAnInvalidDeliveryId_whenGetSaleId_thenThrowsDeliveryNotFoundException() {
        // given
        BDDMockito.given(manufactureDao.getManufacturesMapEntries())
                .willReturn(Map.of(A_SALE_ID, manufacture).entrySet());

        // when
        Executable getManufacturesMapEntries = () -> deliveryRepositoryInMemory.getSaleId(AN_INVALID_DELIVERY_ID);

        // then
        assertThrows(DeliveryNotFoundException.class, getManufacturesMapEntries);
    }

    @Test
    public void givenExistingDelivery_whenUpdateDelivery_thenNewDeliveryIsSaved() {
        // given
        Delivery newDelivery = new Delivery(accountId, A_VALID_DELIVERY_ID, ASSEMBLY_TIME_IN_WEEK);
        BDDMockito.given(manufactureDao.getManufacturesMapEntries())
                .willReturn(Map.of(A_SALE_ID, manufacture).entrySet());

        // when
        deliveryRepositoryInMemory.updateDelivery(newDelivery);

        // then
        BDDMockito.verify(manufactureDao).add(A_SALE_ID, manufacture);
    }
}