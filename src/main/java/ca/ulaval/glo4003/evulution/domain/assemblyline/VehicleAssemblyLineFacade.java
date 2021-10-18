package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class VehicleAssemblyLineFacade implements VehicleAssemblyFacade {

    private final BasicVehicleAssemblyLine vehicleAssemblyLine;
    private final Map<TransactionId, CommandID> transactionIdWithCommandId = new HashMap<>();

    public VehicleAssemblyLineFacade(BasicVehicleAssemblyLine vehicleAssemblyLine,
            List<ModelInformationDto> modelInformationDtos) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.configureAssemblyLine(modelInformationDtos);
    }

    @Override
    public AssemblyStatus getStatus(TransactionId transactionId) {
        CommandID commandId = transactionIdWithCommandId.get(transactionId);
        BuildStatus status = vehicleAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newVehicleCommand(TransactionId transactionId, String command) {
        CommandID commandId = new CommandID(UUID.randomUUID().toString());
        transactionIdWithCommandId.put(transactionId, commandId);
        vehicleAssemblyLine.newCarCommand(commandId, command);
    }

    @Override
    public void advance() {
        vehicleAssemblyLine.advance();
    }

    private void configureAssemblyLine(List<ModelInformationDto> modelInformationDtos) {

        Map<String, Integer> carTimesProduce = new HashMap<>();

        for (ModelInformationDto modelInformationDto : modelInformationDtos) {
            Integer timeToProduce = Integer.parseInt(modelInformationDto.time_to_produce);
            carTimesProduce.put(modelInformationDto.name, timeToProduce);
        }
        vehicleAssemblyLine.configureAssemblyLine(carTimesProduce);
    }
}