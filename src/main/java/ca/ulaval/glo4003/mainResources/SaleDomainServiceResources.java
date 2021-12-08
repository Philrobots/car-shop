package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryDomainService;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;

public class SaleDomainServiceResources {
    private final SaleDomainService saleDomainService;
    private final ManufactureDomainService manufactureDomainService;
    private final DeliveryDomainService deliveryDomainService;

    public SaleDomainServiceResources(FactoryResources factoryResources, RepositoryResources repositoryResources,
                                      InvoicePayment invoicePayment) {
        saleDomainService = new SaleDomainService(repositoryResources.getSaleRepository(),
                repositoryResources.getAccountRepository(), invoicePayment);

        manufactureDomainService = new ManufactureDomainService(factoryResources.getManufactureFactory(),
                repositoryResources.getManufactureRepository(), factoryResources.getCarFactory(),
                factoryResources.getBatteryFactory(), saleDomainService);

        deliveryDomainService = new DeliveryDomainService(factoryResources.getDeliveryDetailsFactory(),
                repositoryResources.getDeliveryRepository());
    }

    public SaleDomainService getSaleDomainService() {
        return saleDomainService;
    }

    public ManufactureDomainService getManufactureDomainService() {
        return manufactureDomainService;
    }

    public DeliveryDomainService getDeliveryDomainService() {
        return deliveryDomainService;
    }
}
