package ca.ulaval.glo4003.evulution.domain.sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SaleFactoryTest {

    private SaleFactory saleFactory;

    @Mock
    private TransactionIdFactory transactionIdFactory;

    private Sale sale;

    private static final String AN_EMAIL = "jo@live.com";

    @BeforeEach
    public void setUp() {
        saleFactory = new SaleFactory(transactionIdFactory);
    }

    @Test
    public void whenCreate_thenSaleHasTheRightEmail() {

        // when
        sale = saleFactory.create(AN_EMAIL);

        // then
        assertEquals(sale.getEmail(), AN_EMAIL);
    }

}
