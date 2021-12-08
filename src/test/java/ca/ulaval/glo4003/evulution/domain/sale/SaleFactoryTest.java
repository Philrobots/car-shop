package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SaleFactoryTest {

    private SaleFactory saleFactory;

    @Mock
    private SaleIdFactory saleIdFactory;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private Token token;

    @Mock
    private InvoiceFactory invoiceFactory;

    @BeforeEach
    public void setUp() {
        saleFactory = new SaleFactory(saleIdFactory, invoiceFactory, tokenRepository);
    }

    @Test
    public void whenCreate_thenSaleIsCreated() throws TokenNotFoundException {
        // when
        Sale sale = saleFactory.create(token);

        // then
        assertNotNull(sale);
    }

    @Test
    public void whenCreate_thenSaleIdFactoryIsCalled() throws TokenNotFoundException {
        // when
        saleFactory.create(token);

        // then
        Mockito.verify(saleIdFactory).create();
    }

    @Test
    public void whenCreate_thenTokenRepositoryIsCalled() throws TokenNotFoundException {
        // when
        saleFactory.create(token);

        // then
        Mockito.verify(tokenRepository).getAccountId(token);
    }
}
