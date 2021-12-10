package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.when;

@ExtendWith(MockitoExtension.class)
public class ManufactureDomainServiceTest {

    @Mock
    ManufactureFactory manufactureFactory;

    @Mock
    ManufactureRepository manufactureRepository;

    @Mock
    CarFactory carFactory;

    @Mock
    BatteryFactory batteryFactory;

    @Mock
    SaleDomainService saleDomainService;

    @Mock
    Delivery delivery;

    SaleId saleId = new SaleId(123);
    Manufacture manufacture = new Manufacture(delivery);

    ManufactureDomainService manufactureDomainService = new ManufactureDomainService(manufactureFactory,
            manufactureRepository, carFactory, batteryFactory, saleDomainService);

    @Test
    void whenCreateManufactureWithDelivery_thenRepositoryIsCalledAndReturnsDeliveryId() {
//        when(manufactureRepository.updateManufacture());
    }

}
