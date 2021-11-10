package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

import java.util.LinkedList;

public class BatteryAssemblyLine {
    private final BatteryAssemblyAdapter batteryAssemblyLineAdapter;
    private final BatteryRepository batteryRepository;
    private boolean isBatteryInProduction = false;
    private AssemblyLineMediator assemblyLineMediator;
    private LinkedList<BatteryProduction> batteryProductionsWaitingList = new LinkedList<>();
    private BatteryProduction currentBatteryProduction;

    public BatteryAssemblyLine(BatteryAssemblyAdapter batteryAssemblyAdapter, BatteryRepository batteryRepository) {
        this.batteryAssemblyLineAdapter = batteryAssemblyAdapter;
        this.batteryRepository = batteryRepository;
    }

    public void addProduction(BatteryProduction batteryProduction) {
        this.batteryProductionsWaitingList.add(batteryProduction);
    }

    public void advance() {
        if (!this.isBatteryInProduction)
            return;

        this.batteryAssemblyLineAdapter.advance();

        AssemblyStatus batteryStatus = this.batteryAssemblyLineAdapter
                .getStatus(this.currentBatteryProduction.getTransactionId());

        if (batteryStatus == AssemblyStatus.ASSEMBLED) {
            this.batteryRepository.add(currentBatteryProduction.getBatteryType(), currentBatteryProduction);
            this.assemblyLineMediator.notify(this.getClass());
            this.isBatteryInProduction = false;
        }
    }

    public void startNext() {
        this.currentBatteryProduction = this.batteryProductionsWaitingList.pop();

        this.batteryAssemblyLineAdapter.newBatteryCommand(this.currentBatteryProduction.getTransactionId(),
                this.currentBatteryProduction.getBatteryType());

        this.isBatteryInProduction = true;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }
}
