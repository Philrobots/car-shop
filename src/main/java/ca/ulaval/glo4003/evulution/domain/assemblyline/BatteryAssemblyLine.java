package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.BatteryAssemblyException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.LinkedList;

public class BatteryAssemblyLine {

    private AssemblyLineMediator assemblyLineMediator;
    private final BatteryAssemblyAdapter batteryAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;
    private LinkedList<Sale> batteries = new LinkedList<Sale>();
    private boolean isBatteryInProduction = false;
    private Sale currentBattery;

    public BatteryAssemblyLine(BatteryAssemblyAdapter batteryFacadeAssemblyLine, int equivalenceOfOneWeekInSeconds) {
        this.batteryAssemblyLineFacade = batteryFacadeAssemblyLine;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
    }

    public void addCommand(Sale sale) {
        this.batteries.add(sale);
    }

    public void advance() {
        if (!this.isBatteryInProduction)
            return;

        this.batteryAssemblyLineFacade.advance();

        AssemblyStatus batteryStatus = this.batteryAssemblyLineFacade.getStatus(this.currentBattery.getTransactionId());

        if (batteryStatus == AssemblyStatus.ASSEMBLED) {
            this.assemblyLineMediator.notify(this.getClass());
            this.isBatteryInProduction = false;
        }

    }

    public void completeBatteryCommand(TransactionId transactionId, Battery battery) {
        try {
            this.batteryAssemblyLineFacade.newBatteryCommand(transactionId, battery.getType());

            boolean isBatteryAssembled = false;

            while (!isBatteryAssembled) {

                AssemblyStatus batteryStatus = this.batteryAssemblyLineFacade.getStatus(transactionId);

                if (batteryStatus != AssemblyStatus.ASSEMBLED) {
                    this.batteryAssemblyLineFacade.advance();
                } else {
                    isBatteryAssembled = true;
                }

                Thread.sleep(timeOfWaitForOneWeek);

            }
            battery.setBatteryAsAssembled();
        } catch (InterruptedException e) {
            throw new BatteryAssemblyException();
        }

    }

    public void startNext() {
        this.currentBattery = this.batteries.pop();

        this.batteryAssemblyLineFacade.newBatteryCommand(this.currentBattery.getTransactionId(),
                this.currentBattery.getBattery().getType());

        this.isBatteryInProduction = true;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }
}
