package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureRepository;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.util.Map;

public class ProductionLine {

    private CarAssemblyLine carAssemblyLine;
    private final BatteryAssemblyLineSequential batteryAssemblyLine;
    private final CompleteAssemblyLineSequential completeAssemblyLine;
    private ManufactureRepository manufactureRepository;
    private SaleDomainService saleDomainService;
    private boolean isShutdown = false;
    private BatteryProductionFactory batteryProductionFactory;
    private CarProductionFactory carProductionFactory;
    private CompleteAssemblyProductionFactory completeAssemblyProductionFactory;
    private ProductionLineEmailNotifier productionLineEmailNotifier;

    public ProductionLine(CarAssemblyLine carAssemblyLine, BatteryAssemblyLineSequential batteryAssemblyLine,
            CompleteAssemblyLineSequential completeAssemblyLine, ManufactureRepository manufactureRepository,
            SaleDomainService saleDomainService, BatteryProductionFactory batteryProductionFactory,
            CarProductionFactory carProductionFactory,
            CompleteAssemblyProductionFactory completeAssemblyProductionFactory,
            ProductionLineEmailNotifier productionLineEmailNotifier) {
        this.carAssemblyLine = carAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeAssemblyLine = completeAssemblyLine;
        this.manufactureRepository = manufactureRepository;
        this.saleDomainService = saleDomainService;
        this.batteryProductionFactory = batteryProductionFactory;
        this.carProductionFactory = carProductionFactory;
        this.completeAssemblyProductionFactory = completeAssemblyProductionFactory;
        this.productionLineEmailNotifier = productionLineEmailNotifier;
    }

    public void advanceAssemblyLines() throws DeliveryIncompleteException, InvalidMappingKeyException,
            SaleNotFoundException, AccountNotFoundException, CarNotAssociatedWithManufactureException {
        this.addNewManufactureToProduction();
        this.carAssemblyLine.advance();
        this.batteryAssemblyLine.advance();
        this.completeAssemblyLine.advance();
    }

    private void addNewManufactureToProduction()
            throws SaleNotFoundException, AccountNotFoundException, CarNotAssociatedWithManufactureException {
        Map<SaleId, Manufacture> manufactures = this.manufactureRepository.getManufacturesReadyForProduction();
        for (Map.Entry<SaleId, Manufacture> manufactureEntry : manufactures.entrySet()) {
            String email = this.saleDomainService.getEmailFromSaleId(manufactureEntry.getKey());
            Manufacture manufacture = manufactureEntry.getValue();
            ProductionId productionId = manufacture.setInProduction();
            this.productionLineEmailNotifier.addEmailWithProduction(productionId, email);
            this.carAssemblyLine.addProduction(manufacture.generateCarProduction(this.carProductionFactory));
            this.batteryAssemblyLine
                    .addProduction(manufacture.generateBatteryProduction(this.batteryProductionFactory));
            this.completeAssemblyLine.addProduction(
                    manufacture.generateCompleteAssemblyProduction(this.completeAssemblyProductionFactory));
            this.manufactureRepository.updateManufacture(manufactureEntry.getKey(), manufacture);
        }
    }

    public void shutdown() throws AssemblyLineIsShutdownException {
        if (this.isShutdown) {
            throw new AssemblyLineIsShutdownException();
        }
        this.carAssemblyLine.shutdown();
        this.batteryAssemblyLine.shutdown();
        this.completeAssemblyLine.shutdown();
        this.isShutdown = true;
        this.productionLineEmailNotifier.sendFireBatteriesEmail();
    }

    public void reactivate() throws AssemblyLineIsNotShutdownException {
        if (!this.isShutdown) {
            throw new AssemblyLineIsNotShutdownException();
        }
        this.carAssemblyLine.reactivate();
        this.batteryAssemblyLine.reactivate();
        this.completeAssemblyLine.reactivate();
        this.isShutdown = false;
    }

    public void setCarAssemblyLine(CarAssemblyLine carAssemblyLine) {
        carAssemblyLine.transferAssemblyLine(this.carAssemblyLine);
        this.carAssemblyLine = carAssemblyLine;
    }
}
