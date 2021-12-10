package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufactureFactoryTest {

    @Mock
    private DeliveryFactory deliveryFactory;

    @Mock
    private AccountId accountId;

    private ManufactureFactory manufactureFactory;

    @BeforeEach
    void setUp() {
        this.manufactureFactory = new ManufactureFactory(deliveryFactory);
    }

    @Test
    public void whenCreate_thenDeliveryFactoryCreates() throws DeliveryIncompleteException {
        // when
        this.manufactureFactory.create(accountId);

        // then
        Mockito.verify(deliveryFactory).create(accountId);
    }

    @Test
    public void whenCreate_thenManufactureIsCreated() throws DeliveryIncompleteException {
        // when
        Manufacture manufacture = this.manufactureFactory.create(accountId);

        // then
        Assertions.assertNotNull(manufacture);
    }
}
