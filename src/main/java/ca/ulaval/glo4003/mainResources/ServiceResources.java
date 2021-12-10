package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoicePayment;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryService;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.manufacture.ManufactureService;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;

public class ServiceResources {
    private final AssemblyLineService assemblyLineService;
    private final DeliveryService deliveryService;
    private final LoginService loginService;
    private final SaleService saleService;
    private final ManufactureService manufactureService;
    private final CustomerService customerService;

    public ServiceResources(FactoryResources factoryResources, RepositoryResources repositoryResources,
            ProductionLineResources productionLineResources, AssemblerResources assemblerResources,
            SaleDomainServiceResources saleDomainServiceResources, SaleValidator saleValidator) {
        assemblyLineService = new AssemblyLineService(productionLineResources.getProductionLine(),
                productionLineResources.getProductionSwitcher());

        deliveryService = new DeliveryService(factoryResources.getDeliveryIdFactory(),
                assemblerResources.getDeliveryCompleteAssembler(), repositoryResources.getDeliveryRepository(),
                saleValidator, saleDomainServiceResources.getDeliveryDomainService(),
                saleDomainServiceResources.getSaleDomainService()); // TODO: fix

        loginService = new LoginService(factoryResources.getTokenFactory(), repositoryResources.getTokenRepository(),
                repositoryResources.getAccountRepository(), assemblerResources.getTokenAssembler());

        saleService = new SaleService(repositoryResources.getSaleRepository(),
                assemblerResources.getSaleCreatedAssembler(), factoryResources.getSaleFactory(),
                saleDomainServiceResources.getManufactureDomainService(),
                saleDomainServiceResources.getSaleDomainService(), factoryResources.getSaleIdFactory(),
                assemblerResources.getTokenAssembler());

        manufactureService = new ManufactureService(saleValidator,
                saleDomainServiceResources.getManufactureDomainService(),
                assemblerResources.getEstimatedRangeAssembler());

        customerService = new CustomerService(repositoryResources.getAccountRepository(),
                factoryResources.getCustomerFactory());
    }

    public AssemblyLineService getAssemblyLineService() {
        return assemblyLineService;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public SaleService getSaleService() {
        return saleService;
    }

    public ManufactureService getManufactureService() {
        return manufactureService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
}
