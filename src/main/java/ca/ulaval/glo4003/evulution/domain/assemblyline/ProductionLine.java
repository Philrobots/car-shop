package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureRepository;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionFactory;
import ca.ulaval.glo4003.evulution.domain.production.complete.CompleteAssemblyProductionFactory;
import ca.ulaval.glo4003.evulution.domain.sale.SaleDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.util.*;

public class ProductionLine {

    private CarAssemblyLine carAssemblyLine;
    private final BatteryAssemblyLineSequential batteryAssemblyLine;
    private final CompleteAssemblyLineSequential completeAssemblyLine;
    private ManufactureRepository manufactureRepository;
    private SaleDomainService saleDomainService;
    private final EmailFactory emailFactory;
    private Set<String> emails = new HashSet<String>();
    private boolean isShutdown = false;
    private BatteryProductionFactory batteryProductionFactory;
    private CarProductionFactory carProductionFactory;
    private CompleteAssemblyProductionFactory completeAssemblyProductionFactory;
    private ProductionType productionType = ProductionType.SEQUENTIAL;

    public ProductionLine(CarAssemblyLineSequential carAssemblyLine, BatteryAssemblyLineSequential batteryAssemblyLine,
                          CompleteAssemblyLineSequential completeAssemblyLine, ManufactureRepository manufactureRepository,
                          SaleDomainService saleDomainService, EmailFactory emailFactory,
                          BatteryProductionFactory batteryProductionFactory, CarProductionFactory carProductionFactory,
                          CompleteAssemblyProductionFactory completeAssemblyProductionFactory) {
        this.carAssemblyLine = carAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeAssemblyLine = completeAssemblyLine;
        this.manufactureRepository = manufactureRepository;
        this.saleDomainService = saleDomainService;
        this.emailFactory = emailFactory;
        this.batteryProductionFactory = batteryProductionFactory;
        this.carProductionFactory = carProductionFactory;
        this.completeAssemblyProductionFactory = completeAssemblyProductionFactory;
    }

    public void advanceAssemblyLines() throws DeliveryIncompleteException, InvalidMappingKeyException, EmailException,
            SaleNotFoundException, AccountNotFoundException {
        this.addNewManufactureToProduction();
        this.carAssemblyLine.advance();
        this.batteryAssemblyLine.advance();
        this.completeAssemblyLine.advance();
    }

    private void addNewManufactureToProduction()
            throws EmailException, SaleNotFoundException, AccountNotFoundException {
        Map<SaleId, Manufacture> manufactures = this.manufactureRepository.getManufacturesReadyForProduction();
        for (Map.Entry<SaleId, Manufacture> manufactureEntry : manufactures.entrySet()) {
            String email = saleDomainService.getEmailFromSaleId(manufactureEntry.getKey());
            emails.add(email);
            Manufacture manufacture = manufactureEntry.getValue();
            manufacture.setInProduction();
            this.carAssemblyLine.addProduction(manufacture.generateCarProduction(email, carProductionFactory));
            this.batteryAssemblyLine
                    .addProduction(manufacture.generateBatteryProduction(email, batteryProductionFactory));
            this.completeAssemblyLine.addProduction(
                    manufacture.generateCompleteAssemblyProduction(email, completeAssemblyProductionFactory));
            manufactureRepository.updateManufacture(manufactureEntry.getKey(), manufacture);
        }
    }

    public void shutdown() throws AssemblyLineIsShutdownException, EmailException {
        if (isShutdown)
            throw new AssemblyLineIsShutdownException();
        this.carAssemblyLine.shutdown();
        this.batteryAssemblyLine.shutdown();
        this.completeAssemblyLine.shutdown();
        this.isShutdown = true;
        this.sendEmailToConsumers();
    }

    private void sendEmailToConsumers() throws EmailException {
        List<String> recipients = new ArrayList<String>();
        // TODO la liste de emails est jamais vidée
        recipients.addAll(this.emails);
        this.emailFactory.createAssemblyFireBatteriesEmail(recipients).send();
    }

    public void reactivate() throws AssemblyLineIsNotShutdownException, EmailException {
        if (!isShutdown)
            throw new AssemblyLineIsNotShutdownException();
        this.carAssemblyLine.reactivate();
        this.batteryAssemblyLine.reactivate();
        this.completeAssemblyLine.reactivate();
        this.isShutdown = false;
    }

    public void setCarAssemblyLine(CarAssemblyLine carAssemblyLine){
        this.carAssemblyLine = carAssemblyLine;
    }

}
