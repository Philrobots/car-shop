package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryAssemblyLine {

    private final BatteryAssemblyFacade batteryAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;

    public BatteryAssemblyLine(BatteryAssemblyFacade batteryFacadeAssemblyLine, int equivalenceOfOneWeekInSeconds) {
        this.batteryAssemblyLineFacade = batteryFacadeAssemblyLine;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
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
        }

    }
}
