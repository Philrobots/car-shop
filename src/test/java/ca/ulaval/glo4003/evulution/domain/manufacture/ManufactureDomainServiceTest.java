package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ManufactureDomainServiceTest {

    private static final BigDecimal A_PRICE = BigDecimal.ONE;
    private static final int A_RANGE = 100;
    private static final String A_NAME = "name";
    private static final String A_COLOR = "color";
    private static final String A_BATTERY_TYPE = "STANDARD";

    @Mock
    private ManufactureFactory manufactureFactory;

    @Mock
    private ManufactureRepository manufactureRepository;

    @Mock
    private CarFactory carFactory;

    @Mock
    private BatteryFactory batteryFactory;

    @Mock
    private Battery battery;

    @Mock
    private SaleDomainService saleDomainService;

    @Mock
    private AccountId accountId;

    @Mock
    private SaleId saleId;

    @Mock
    private Manufacture manufacture;

    @Mock
    private Car car;

    private ManufactureDomainService manufactureDomainService;

    @BeforeEach
    void setUp() {
        this.manufactureDomainService = new ManufactureDomainService(manufactureFactory, manufactureRepository,
                carFactory, batteryFactory, saleDomainService);
    }

    @Test
    public void whenCreateManufactureWithDelivery_thenManufactureFactoryCreates() throws DeliveryIncompleteException {
        // given
        BDDMockito.given(manufactureFactory.create(accountId)).willReturn(manufacture);

        // when
        this.manufactureDomainService.createManufactureWithDelivery(accountId, saleId);

        // then
        Mockito.verify(manufactureFactory).create(accountId);
    }

    @Test
    public void whenCreateManufactureWithDelivery_thenManufactureRepositoryAddsManufacture()
            throws DeliveryIncompleteException {
        // given
        BDDMockito.given(manufactureFactory.create(accountId)).willReturn(manufacture);

        // when
        this.manufactureDomainService.createManufactureWithDelivery(accountId, saleId);

        // then
        Mockito.verify(manufactureRepository).addManufacture(saleId, manufacture);
    }

    @Test
    public void whenCreateManufactureWithDelivery_thenManufactureGetsDeliveryId() throws DeliveryIncompleteException {
        // given
        BDDMockito.given(manufactureFactory.create(accountId)).willReturn(manufacture);

        // when
        this.manufactureDomainService.createManufactureWithDelivery(accountId, saleId);

        // then
        Mockito.verify(manufacture).getDeliveryId();
    }

    @Test
    public void whenAddCar_thenManufactureRepositoryGetsSaleById() throws BadCarSpecsException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(carFactory.create(A_NAME, A_COLOR)).willReturn(car);

        // when
        this.manufactureDomainService.addCar(saleId, A_NAME, A_COLOR);

        // then
        Mockito.verify(manufactureRepository).getBySaleId(saleId);
    }

    @Test
    public void whenAddCar_thenCarFactoryCreates() throws BadCarSpecsException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(carFactory.create(A_NAME, A_COLOR)).willReturn(car);

        // when
        this.manufactureDomainService.addCar(saleId, A_NAME, A_COLOR);

        // then
        Mockito.verify(carFactory).create(A_NAME, A_COLOR);
    }

    @Test
    public void whenAddCar_thenManufactureSetsCar() throws BadCarSpecsException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(carFactory.create(A_NAME, A_COLOR)).willReturn(car);

        // when
        this.manufactureDomainService.addCar(saleId, A_NAME, A_COLOR);

        // then
        Mockito.verify(manufacture).setCar(car);
    }

    @Test
    public void whenAddCar_thenManufactureRepositoryUpdatesManufacture()
            throws BadCarSpecsException, SaleNotFoundException {

        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(carFactory.create(A_NAME, A_COLOR)).willReturn(car);

        // when
        this.manufactureDomainService.addCar(saleId, A_NAME, A_COLOR);

        // then
        Mockito.verify(manufactureRepository).updateManufacture(saleId, manufacture);
    }

    @Test
    public void whenAddCar_thenSaleDomainServiceSetsVehiclePrice() throws BadCarSpecsException, SaleNotFoundException {

        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(carFactory.create(A_NAME, A_COLOR)).willReturn(car);
        BDDMockito.given(car.getBasePrice()).willReturn(A_PRICE);

        // when
        this.manufactureDomainService.addCar(saleId, A_NAME, A_COLOR);

        // then
        Mockito.verify(saleDomainService).setVehiclePrice(saleId, car.getBasePrice());
    }

    @Test
    public void whenAddBattery_thenManufactureRepositoryGetsSaleById()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Mockito.verify(manufactureRepository).getBySaleId(saleId);
    }

    @Test
    public void whenAddBattery_thenBatteryFactoryCreates()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Mockito.verify(batteryFactory).create(A_BATTERY_TYPE);
    }

    @Test
    public void whenAddBattery_thenManufactureSetsBattery()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Mockito.verify(manufacture).setBattery(battery);
    }

    @Test
    public void whenAddBattery_thenManufactureRepositoryUpdatesManufacture()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Mockito.verify(manufactureRepository).updateManufacture(saleId, manufacture);
    }

    @Test
    public void whenAddBattery_thenSaleDomainServiceSetsBatteryPrice()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(battery.getPrice()).willReturn(A_PRICE);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Mockito.verify(saleDomainService).setBatteryPrice(saleId, A_PRICE);
    }

    @Test
    public void whenAddBattery_thenReturnEstimatedRange()
            throws BadCarSpecsException, CarNotChosenBeforeBatteryException, SaleNotFoundException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);
        BDDMockito.given(batteryFactory.create(A_BATTERY_TYPE)).willReturn(battery);
        BDDMockito.given(battery.getPrice()).willReturn(A_PRICE);
        BDDMockito.given(manufacture.setBattery(battery)).willReturn(A_RANGE);

        // when
        int range = this.manufactureDomainService.addBattery(saleId, A_BATTERY_TYPE);

        // then
        Assertions.assertEquals(range, A_RANGE);
    }

    @Test
    public void whenSetManufactureReadyToProduce_thenManufactureRepositoryGetsBySaleId()
            throws MissingElementsForSaleException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);

        // when
        this.manufactureDomainService.setManufactureReadyToProduce(saleId);

        // then
        Mockito.verify(manufactureRepository).getBySaleId(saleId);
    }

    @Test
    public void whenSetManufactureReadyToProduce_thenManufactureSetsReadyToProduce()
            throws MissingElementsForSaleException {
        // given
        BDDMockito.given(manufactureRepository.getBySaleId(saleId)).willReturn(manufacture);

        // when
        this.manufactureDomainService.setManufactureReadyToProduce(saleId);

        // then
        Mockito.verify(manufacture).setReadyToProduce();
    }

}
