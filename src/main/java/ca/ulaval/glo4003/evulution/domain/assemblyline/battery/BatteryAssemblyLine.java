package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

import java.util.LinkedList;
import java.util.List;

public class BatteryAssemblyLine {

    private LinkedList<BatteryProduction> batteryProductionsWaitingList = new LinkedList<>();

    private final BatteryAssemblyAdapter batteryAssemblyLineAdapter;
    private final BatteryRepository batteryRepository;
    private AssemblyLineMediator assemblyLineMediator;
    private BatteryProduction currentBatteryProduction;
    private final EmailFactory emailFactory;

    private boolean isBatteryInProduction = false;
    private boolean isBatteryInFire = false;

    public BatteryAssemblyLine(BatteryAssemblyAdapter batteryAssemblyAdapter, BatteryRepository batteryRepository,
            EmailFactory emailFactory) {
        this.batteryAssemblyLineAdapter = batteryAssemblyAdapter;
        this.batteryRepository = batteryRepository;
        this.emailFactory = emailFactory;
    }

    public void addProduction(BatteryProduction batteryProduction) {
        this.batteryProductionsWaitingList.add(batteryProduction);
    }

    public void advance() {
        if (!this.isBatteryInProduction || this.isBatteryInFire) {
            System.out.println("BatteryAssemblyLine skipped");
            return;
        }

        System.out.println("BatteryAssemblyLine advance with: " + this.currentBatteryProduction.getBatteryType());

        this.batteryAssemblyLineAdapter.advance();

        AssemblyStatus batteryStatus = this.batteryAssemblyLineAdapter
                .getStatus(this.currentBatteryProduction.getTransactionId());

        if (batteryStatus == AssemblyStatus.ASSEMBLED) {
            this.batteryRepository.add(currentBatteryProduction.getBatteryType(), currentBatteryProduction);
            this.assemblyLineMediator.notify(this.getClass());
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

        for (BatteryProduction batteryProduction : batteryRepository.getAndSendToProduction()) {
            this.batteryProductionsWaitingList.addFirst(batteryProduction);
        }

        if (!batteryProductionsWaitingList.isEmpty() && (assemblyLineMediator.getState() == AssemblyState.BATTERY
                || assemblyLineMediator.getState() == AssemblyState.ASSEMBLY))
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
        emailFactory.createBatteryBuiltEmail(List.of(this.currentBatteryProduction.getEmail()),
                this.currentBatteryProduction.getProductionTimeInWeeks()).send();
        this.batteryAssemblyLineAdapter.newBatteryCommand(this.currentBatteryProduction.getTransactionId(),
                this.currentBatteryProduction.getBatteryType());

        this.isBatteryInProduction = true;
    }
}
