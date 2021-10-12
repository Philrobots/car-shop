package ca.ulaval.glo4003.evulution.api.assemblyline;

import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Map;

public class BatteryAssemblyLineFacade implements AssemblyLine {

    BatteryAssemblyLine batteryAssemblyLine = new BasicBatteryAsssemblyLine();

    @Override
    public AssemblyStatus getStatus(TransactionId transactionId) {
        CommandID commandId = new CommandID(transactionId.toString());
        BuildStatus status = batteryAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newCommand(TransactionId transactionId, String command) {
        CommandID commandID = new CommandID(transactionId.toString());
        batteryAssemblyLine.newBatteryCommand(commandID, command);
    }

    @Override
    public void configureAssemblyLine(Map<String, Integer> timeByCommand) {
        batteryAssemblyLine.configureAssemblyLine(timeByCommand);
    }

    @Override
    public void advance() {
        batteryAssemblyLine.advance();
    }
}
