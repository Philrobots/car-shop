package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CarAssemblyLineAdapter implements CarAssemblyAdapter {

    private final BasicVehicleAssemblyLine vehicleAssemblyLine;
    private final Map<ProductionId, CommandID> productionIdWithCommandId = new HashMap<>();

    public CarAssemblyLineAdapter(BasicVehicleAssemblyLine vehicleAssemblyLine,
            List<ModelInformationDto> modelInformationDtos) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.configureAssemblyLine(modelInformationDtos);
    }

    @Override
    public AssemblyStatus getStatus(ProductionId productionId) {
        CommandID commandId = productionIdWithCommandId.get(productionId);
        BuildStatus status = vehicleAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newVehicleCommand(ProductionId productionId, String command) {
        CommandID commandId = new CommandID(UUID.randomUUID().toString());
        productionIdWithCommandId.put(productionId, commandId);
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
