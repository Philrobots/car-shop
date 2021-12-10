package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;

public class ProductionLineResources {
    private final ProductionLine productionLine;
    private ProductionSwitcher productionSwitcher;
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    public ProductionLineResources(FactoryResources factoryResources, RepositoryResources repositoryResources,
            SaleDomainServiceResources saleDomainServiceResources, AssemblyLineResources assemblyLineResources) {

        productionLineEmailNotifier = new ProductionLineEmailNotifier(factoryResources.getEmailFactory());

        productionLine = new ProductionLine(assemblyLineResources.getCarAssemblyLineSequential(),
                assemblyLineResources.getBatteryAssemblyLine(), assemblyLineResources.getCompleteAssemblyLine(),
                repositoryResources.getManufactureRepository(), saleDomainServiceResources.getSaleDomainService(),
                factoryResources.getBatteryProductionFactory(), factoryResources.getCarProductionFactory(),
                factoryResources.getCompleteAssemblyProductionFactory(), productionLineEmailNotifier); // TODO:

        productionSwitcher = new ProductionSwitcher(assemblyLineResources.getCarAssemblyLineJIT(),
                assemblyLineResources.getCarAssemblyLineSequential(), productionLine,
                assemblyLineResources.getMediatorSwitcher());
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public ProductionSwitcher getProductionSwitcher() {
        return this.productionSwitcher;
    }
}
