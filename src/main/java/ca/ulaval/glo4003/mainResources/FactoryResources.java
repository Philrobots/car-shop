package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDetailsFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.EmailSender;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureFactory;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProductionFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleIdFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;

public class FactoryResources {
    private final SaleIdFactory saleIdFactory = new SaleIdFactory();
    private final DeliveryIdFactory deliveryIdFactory = new DeliveryIdFactory();
    private final DeliveryFactory deliveryFactory;
    private final DeliveryDetailsFactory deliveryDetailsFactory = new DeliveryDetailsFactory(
            JsonFileMapper.parseDeliveryLocations());
    private final ManufactureFactory manufactureFactory;
    private final InvoiceFactory invoiceFactory = new InvoiceFactory();
    private final SaleFactory saleFactory;
    private final CarFactory carFactory = new CarFactory(JsonFileMapper.parseModels());
    private final BatteryFactory batteryFactory = new BatteryFactory(JsonFileMapper.parseBatteries());
    private final EmailFactory emailFactory;
    private final CustomerFactory customerFactory;
    private final TokenFactory tokenFactory = new TokenFactory();
    private final BatteryProductionFactory batteryProductionFactory = new BatteryProductionFactory();
    private final CarProductionFactory carProductionFactory = new CarProductionFactory();
    private final CompleteAssemblyProductionFactory completeAssemblyProductionFactory = new CompleteAssemblyProductionFactory();

    public FactoryResources(Integer assembleTimeInWeeks, EmailSender emailSender, TokenRepository tokenRepository) {
        deliveryFactory = new DeliveryFactory(assembleTimeInWeeks, deliveryIdFactory);
        saleFactory = new SaleFactory(saleIdFactory, invoiceFactory, tokenRepository);
        emailFactory = new EmailFactory(emailSender);
        customerFactory = new CustomerFactory();
        manufactureFactory = new ManufactureFactory(deliveryFactory);
    }

    public SaleIdFactory getSaleIdFactory() {
        return saleIdFactory;
    }

    public DeliveryIdFactory getDeliveryIdFactory() {
        return deliveryIdFactory;
    }

    public DeliveryFactory getDeliveryFactory() {
        return deliveryFactory;
    }

    public DeliveryDetailsFactory getDeliveryDetailsFactory() {
        return deliveryDetailsFactory;
    }

    public ManufactureFactory getManufactureFactory() {
        return manufactureFactory;
    }

    public InvoiceFactory getInvoiceFactory() {
        return invoiceFactory;
    }

    public SaleFactory getSaleFactory() {
        return saleFactory;
    }

    public CarFactory getCarFactory() {
        return carFactory;
    }

    public BatteryFactory getBatteryFactory() {
        return batteryFactory;
    }

    public EmailFactory getEmailFactory() {
        return emailFactory;
    }

    public CustomerFactory getCustomerFactory() {
        return customerFactory;
    }

    public TokenFactory getTokenFactory() {
        return tokenFactory;
    }

    public BatteryProductionFactory getBatteryProductionFactory() {
        return batteryProductionFactory;
    }

    public CarProductionFactory getCarProductionFactory() {
        return carProductionFactory;
    }

    public CompleteAssemblyProductionFactory getCompleteAssemblyProductionFactory() {
        return completeAssemblyProductionFactory;
    }
}
