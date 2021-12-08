package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.BatteryInformationDto;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BatteryAssemblyLineAdapter implements BatteryAssemblyAdapter {

    private final BasicBatteryAssemblyLine basicBatteryAssemblyLine;
    private final Map<ProductionId, CommandID> productionIdWithCommandId = new HashMap<>();

    public BatteryAssemblyLineAdapter(BasicBatteryAssemblyLine basicBatteryAssemblyLine,
            List<BatteryInformationDto> batteries) {
        this.basicBatteryAssemblyLine = basicBatteryAssemblyLine;
        this.configureBatteryProductionTime(batteries);
    }

    @Override
    public AssemblyStatus getStatus(ProductionId productionId) {
        CommandID commandId = productionIdWithCommandId.get(productionId);
        BuildStatus status = this.basicBatteryAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newBatteryCommand(ProductionId productionId, String command) {
        CommandID commandId = new CommandID(UUID.randomUUID().toString());
        this.productionIdWithCommandId.put(productionId, commandId);
        this.basicBatteryAssemblyLine.newBatteryCommand(commandId, command);
    }

    @Override
    public void advance() {
        this.basicBatteryAssemblyLine.advance();
    }

    private void configureBatteryProductionTime(List<BatteryInformationDto> batteries) {
        Map<String, Integer> batteriesWithTimeToProduce = new HashMap<>();

        for (BatteryInformationDto b : batteries) {
            batteriesWithTimeToProduce.put(b.name, Integer.parseInt(b.time_to_produce));
        }

        this.basicBatteryAssemblyLine.configureAssemblyLine(batteriesWithTimeToProduce);
    }
}
