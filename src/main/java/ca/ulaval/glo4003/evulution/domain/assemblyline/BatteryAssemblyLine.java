package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryAssemblyLine {

    private final BatteryAssemblyFacade batteryAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;


    public BatteryAssemblyLine(BatteryAssemblyFacade batteryFacadeAssemblyLine, int equivalenceOfOneWeekInSeconds) {
        this.batteryAssemblyLineFacade = batteryFacadeAssemblyLine;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;
    }

    public void completeBatteryCommand(Sale sale) {
        try {
            TransactionId transactionId = sale.getTransactionId();
            String batteryType = sale.getBatteryType();
            this.batteryAssemblyLineFacade.newBatteryCommand(transactionId, batteryType);

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
        } catch (InterruptedException e) { }

    }
}
