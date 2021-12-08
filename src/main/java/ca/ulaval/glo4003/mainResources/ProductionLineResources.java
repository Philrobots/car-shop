package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineJustInTime;
import ca.ulaval.glo4003.evulution.domain.assemblyline.switchProduction.ProductionSwitcher;

public class ProductionLineResources {
    private final ProductionLine productionLine;
    private ProductionSwitcher productionSwitcher;

    public ProductionLineResources(FactoryResources factoryResources, RepositoryResources repositoryResources,
            SaleDomainServiceResources saleDomainServiceResources, AssemblyLineResources assemblyLineResources) {

        productionLine = new ProductionLine(assemblyLineResources.getCarAssemblyLine(),
                assemblyLineResources.getBatteryAssemblyLine(), assemblyLineResources.getCompleteAssemblyLine(),
                repositoryResources.getManufactureRepository(), saleDomainServiceResources.getSaleDomainService(),
                factoryResources.getEmailFactory(), factoryResources.getBatteryProductionFactory(),
                factoryResources.getCarProductionFactory(), factoryResources.getCompleteAssemblyProductionFactory()); // TODO:
                                                                                                                      // fix
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public ProductionSwitcher getProductionSwitcher() {
        return this.productionSwitcher;
    }
}
