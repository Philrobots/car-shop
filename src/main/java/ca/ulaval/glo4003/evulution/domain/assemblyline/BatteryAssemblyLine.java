package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class BatteryAssemblyLine {

    private FacadeAssemblyLine batteryFacadeAssemblyLine;

    public BatteryAssemblyLine(FacadeAssemblyLine batteryFacadeAssemblyLine) {
        this.batteryFacadeAssemblyLine = batteryFacadeAssemblyLine;
    }

    public void completeBatteryCommand(Sale sale) {
        TransactionId transactionId = sale.getTransactionId();
        String batteryType = sale.getBatteryType();
        this.batteryFacadeAssemblyLine.newCommand(transactionId, batteryType);

        boolean isBatteryAssembled = false;

        while (!isBatteryAssembled) {

            AssemblyStatus batteryStatus = this.batteryFacadeAssemblyLine.getStatus(transactionId);

            if (batteryStatus != AssemblyStatus.ASSEMBLED) {
                this.batteryFacadeAssemblyLine.advance();
            } else {
                isBatteryAssembled = true;
            }
        }
    }
}
