package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.MissingElementsForSaleException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

public class ManufactureDomainService {
    private ManufactureFactory manufactureFactory;
    private ManufactureRepository manufactureRepository;
    private CarFactory carFactory;
    private BatteryFactory batteryFactory;
    private SaleDomainService saleDomainService;

    public ManufactureDomainService(ManufactureFactory manufactureFactory, ManufactureRepository manufactureRepository,
                                    CarFactory carFactory, BatteryFactory batteryFactory, SaleDomainService saleDomainService) {
        this.manufactureFactory = manufactureFactory;
        this.manufactureRepository = manufactureRepository;
        this.carFactory = carFactory;
        this.batteryFactory = batteryFactory;
        this.saleDomainService = saleDomainService;
    }

    public DeliveryId createManufactureWithDelivery(AccountId accountId, SaleId saleId)
            throws DeliveryIncompleteException {
        Manufacture manufacture = manufactureFactory.create(accountId);
        manufactureRepository.addManufacture(saleId, manufacture);
        return manufacture.getDeliveryId();
    }

    public void addCar(SaleId saleId, String name, String color) throws BadCarSpecsException, SaleNotFoundException {
        Manufacture manufacture = manufactureRepository.getBySaleId(saleId);
        Car car = this.carFactory.create(name, color);
        manufacture.setCar(car);
        manufactureRepository.updateManufacture(saleId, manufacture);
        saleDomainService.addPrice(saleId, car.getBasePrice());
    }

    public int addBattery(SaleId saleId, String type)
            throws BadCarSpecsException, SaleNotFoundException, CarNotChosenBeforeBatteryException {
        Manufacture manufacture = manufactureRepository.getBySaleId(saleId);
        Battery battery = this.batteryFactory.create(type);
        saleDomainService.addPrice(saleId, battery.getPrice());
        return manufacture.addBattery(battery);
    }

    public void setManufactureReadyToProduce(SaleId saleId) throws MissingElementsForSaleException {
        Manufacture manufacture = manufactureRepository.getBySaleId(saleId);
        manufacture.setReadyToProduce();
    }
}
