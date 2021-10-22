package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

import java.util.LinkedList;

public class BatteryAssemblyLine {

    private AssemblyLineMediator assemblyLineMediator;
    private final BatteryAssemblyAdapter batteryAssemblyLineAdapter;
    private LinkedList<BatteryProduction> batteryProductionsWaitingList = new LinkedList<>();
    private boolean isBatteryInProduction = false;
    private BatteryProduction currentBatteryProduction;

    public BatteryAssemblyLine(BatteryAssemblyAdapter batteryAssemblyAdapter) {
        this.batteryAssemblyLineAdapter = batteryAssemblyAdapter;
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
