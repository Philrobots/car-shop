package ca.ulaval.glo4003.evulution.api.assemblyline;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Map;

public class VehicleAssemblyLineFacade implements BatteryAssemblyLine {

    BasicVehicleAssemblyLine vehicleAssemblyLine = new BasicVehicleAssemblyLine();

    @Override
    public AssemblyStatus getStatus(TransactionId transactionId) {
        CommandID commandId = new CommandID(transactionId.toString());
        BuildStatus status = vehicleAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newCommand(TransactionId transactionId, String command) {
        CommandID commandID = new CommandID(transactionId.toString());
        vehicleAssemblyLine.newCarCommand(commandID, command);
    }

    @Override
    public void configureAssemblyLine(Map<String, Integer> timeByCommand) {
        vehicleAssemblyLine.configureAssemblyLine(timeByCommand);
    }

    @Override
    public void advance() {
        vehicleAssemblyLine.advance();
    }
}
