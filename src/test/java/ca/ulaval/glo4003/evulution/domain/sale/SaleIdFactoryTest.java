package ca.ulaval.glo4003.evulution.domain.sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SaleIdFactoryTest {

    private SaleIdFactory saleIdFactory;

    @BeforeEach
    public void setUp() {
        saleIdFactory = new SaleIdFactory();
    }

    @Test
    public void whenCreate_thenASaleIdIsCreated() {
        // when
        SaleId saleId = saleIdFactory.create();

        // then
        assertNotNull(saleId);
    }

    @Test
    public void givenSaleId_whenCreateFromInt_thenASaleIdIsCreated() {
        // given
        int saleIdRequest = 1;

        // when
        SaleId saleId = saleIdFactory.createFromInt(saleIdRequest);

        // then
        assertNotNull(saleId);
    }
}
