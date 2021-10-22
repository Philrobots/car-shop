package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

import java.util.LinkedList;

public class BatteryAssemblyLine {

    private AssemblyLineMediator assemblyLineMediator;
    private final BatteryAssemblyAdapter batteryAssemblyLineFacade;
    private LinkedList<BatteryProduction> batteryProductionsWaitingList = new LinkedList<>();
    private boolean isBatteryInProduction = false;
    private BatteryProduction currentBatteryProduction;

    public BatteryAssemblyLine(BatteryAssemblyAdapter batteryFacadeAssemblyLine) {
        this.batteryAssemblyLineFacade = batteryFacadeAssemblyLine;
    }

    public void addProduction(BatteryProduction batteryProduction) {
        this.batteryProductionsWaitingList.add(batteryProduction);
    }

    public void advance() {
        if (!this.isBatteryInProduction)
            return;

        this.batteryAssemblyLineFacade.advance();

        AssemblyStatus batteryStatus = this.batteryAssemblyLineFacade.getStatus(this.currentBatteryProduction.getTransactionId());

        if (batteryStatus == AssemblyStatus.ASSEMBLED) {
            System.out.println("BATTERY ASSEMBLED");
            this.assemblyLineMediator.notify(this.getClass());
            this.isBatteryInProduction = false;
        }

    }

//    public void completeBatteryCommand(TransactionId transactionId, Battery battery) {
//        try {
//            this.batteryAssemblyLineFacade.newBatteryCommand(transactionId, battery.getType());
//
//            boolean isBatteryAssembled = false;
//
//            while (!isBatteryAssembled) {
//
//                AssemblyStatus batteryStatus = this.batteryAssemblyLineFacade.getStatus(transactionId);
//
//                if (batteryStatus != AssemblyStatus.ASSEMBLED) {
//                    this.batteryAssemblyLineFacade.advance();
//                } else {
//                    isBatteryAssembled = true;
//                }
//
//
//            }
//            battery.setBatteryAsAssembled();
//        } catch (InterruptedException e) {
//            throw new BatteryAssemblyException();
//        }
//
//    }

    public void startNext() {
        this.currentBatteryProduction = this.batteryProductionsWaitingList.pop();

        this.batteryAssemblyLineFacade.newBatteryCommand(this.currentBatteryProduction.getTransactionId(),
                this.currentBatteryProduction.getBatteryType());

        this.isBatteryInProduction = true;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }
}
