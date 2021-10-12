package ca.ulaval.glo4003.evulution.api.assemblyline;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLine;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class VehicleAssemblyLineFacade implements AssemblyLine {

    BasicVehicleAssemblyLine vehicleAssemblyLine = new BasicVehicleAssemblyLine();

    Map<TransactionId, CommandID> transactionIdWithCommandId = new HashMap<>();

    @Override
    public AssemblyStatus getStatus(TransactionId transactionId) {
        CommandID commandId = transactionIdWithCommandId.get(transactionId);
        BuildStatus status = vehicleAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newCommand(TransactionId transactionId, String command) {
        CommandID commandId = new CommandID(UUID.randomUUID().toString());
        transactionIdWithCommandId.put(transactionId, commandId);
        vehicleAssemblyLine.newCarCommand(commandId, command);
    }

    @Override
    public void configureAssemblyLine(Map<String, Integer> productionTimeByVehicleType) {
        vehicleAssemblyLine.configureAssemblyLine(productionTimeByVehicleType);
    }

    @Override
    public void advance() {
        vehicleAssemblyLine.advance();
    }
}