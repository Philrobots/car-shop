package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.LinkedList;

public class BatteryAssemblyLineSequential implements BatteryAssemblyLine {

    private final BatteryAssemblyAdapter batteryAssemblyLineAdapter;
    private final BatteryProductionRepository batteryProductionRepository;
    private final EmailFactory emailFactory;
    private ProductionLineEmailNotifier productionLineEmailNotifier;
    private LinkedList<BatteryProduction> batteryProductionsWaitingList = new LinkedList<>();
    private AssemblyLineMediator assemblyLineMediator;
    private BatteryProduction currentBatteryProduction;
    private boolean isBatteryInProduction = false;
    private boolean isBatteryInFire = false;

    public BatteryAssemblyLineSequential(BatteryAssemblyAdapter batteryAssemblyAdapter,
            BatteryProductionRepository batteryProductionRepository, EmailFactory emailFactory, ProductionLineEmailNotifier productionLineEmailNotifier) {
        this.batteryAssemblyLineAdapter = batteryAssemblyAdapter;
        this.batteryProductionRepository = batteryProductionRepository;
        this.emailFactory = emailFactory;
        this.productionLineEmailNotifier = productionLineEmailNotifier;
    }

    public void addProduction(BatteryProduction batteryProduction) {
        this.batteryProductionsWaitingList.add(batteryProduction);
    }

    public void advance() {
        if (!this.isBatteryInProduction || this.isBatteryInFire) {
            System.out.println("Skipping Battery");
            return;
        }

        System.out.println("Building Battery");
        boolean isBatteryAssembled = currentBatteryProduction.advance(batteryAssemblyLineAdapter);

        if (isBatteryAssembled) {
            System.out.println("Battery is assembled");
            this.batteryProductionRepository.add(currentBatteryProduction);
            this.assemblyLineMediator.notify(BatteryAssemblyLine.class);
            this.isBatteryInProduction = false;
        }
    }

    public void shutdown() {
        this.isBatteryInFire = true;
        if (isBatteryInProduction)
            this.batteryProductionsWaitingList.add(currentBatteryProduction);
        this.isBatteryInProduction = false;
    }

    public void reactivate() {
        this.isBatteryInFire = false;

        for (BatteryProduction batteryProduction : batteryProductionRepository.getAndSendToProduction()) {
            this.batteryProductionsWaitingList.addFirst(batteryProduction);
        }

        if (!batteryProductionsWaitingList.isEmpty() && this.assemblyLineMediator.shouldBatteryReactivateProduction())
            setUpNextBatteryForProduction();
    }

    public void startNext() {
        if (this.isBatteryInFire)
            return;

        setUpNextBatteryForProduction();
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    private void setUpNextBatteryForProduction() {
        this.currentBatteryProduction = this.batteryProductionsWaitingList.pop();
        this.productionLineEmailNotifier.sendBatteryStartedEmail(this.currentBatteryProduction.getProductionId(),
                this.currentBatteryProduction.getProductionTimeInWeeks());
        this.currentBatteryProduction.newBatteryCommand(batteryAssemblyLineAdapter);

        this.isBatteryInProduction = true;
    }
}
