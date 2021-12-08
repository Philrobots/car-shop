package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryRepository;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.account.AccountRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.BatteryRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.VehicleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.DeliveryRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.manufacture.ManufactureDao;
import ca.ulaval.glo4003.evulution.infrastructure.manufacture.ManufactureRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.sale.SaleRepositoryInMemory;
import ca.ulaval.glo4003.evulution.infrastructure.token.TokenRepositoryInMemory;

public class RepositoryResources {

    private final ManufactureDao manufactureDao = new ManufactureDao();
    private final AccountRepository accountRepository = new AccountRepositoryInMemory();
    private final TokenRepository tokenRepository = new TokenRepositoryInMemory();
    private final SaleRepository saleRepository = new SaleRepositoryInMemory();
    private final DeliveryRepository deliveryRepository = new DeliveryRepositoryInMemory(manufactureDao);
    private final VehicleRepository vehicleRepository = new VehicleRepositoryInMemory();
    private final BatteryRepository batteryRepository = new BatteryRepositoryInMemory();
    private final ManufactureRepository manufactureRepository = new ManufactureRepositoryInMemory(manufactureDao);

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public TokenRepository getTokenRepository() {
        return tokenRepository;
    }

    public SaleRepository getSaleRepository() {
        return saleRepository;
    }

    public DeliveryRepository getDeliveryRepository() {
        return deliveryRepository;
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public BatteryRepository getBatteryRepository() {
        return batteryRepository;
    }

    public ManufactureRepository getManufactureRepository() {
        return manufactureRepository;
    }
}
