package ca.ulaval.glo4003.evulution.domain.assemblyline;
import ca.ulaval.glo4003.evulution.car_manufacture.BuildStatus;
import ca.ulaval.glo4003.evulution.car_manufacture.CommandID;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.car.BatteryInformationDto;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BatteryAssemblyLineFacade implements BatteryAssemblyFacade  {

    private BasicBatteryAssemblyLine batteryAssemblyLine = new BasicBatteryAssemblyLine();
    private final Map<TransactionId, CommandID> transactionIdWithCommandId = new HashMap<>();

    public BatteryAssemblyLineFacade(List<BatteryInformationDto> batteries) {
        this.configureBatteryProductionTime(batteries);
    }

    @Override
    public AssemblyStatus getStatus(TransactionId transactionId) {
        CommandID commandId = transactionIdWithCommandId.get(transactionId);
        BuildStatus status = batteryAssemblyLine.getBuildStatus(commandId);
        return AssemblyStatus.valueOf(status.toString());
    }

    @Override
    public void newBatteryCommand(TransactionId transactionId, String command) {
        CommandID commandId = new CommandID(UUID.randomUUID().toString());
        transactionIdWithCommandId.put(transactionId, commandId);
        batteryAssemblyLine.newBatteryCommand(commandId, command);
    }

    @Override
    public void advance() {
        batteryAssemblyLine.advance();
    }

    private void configureBatteryProductionTime(List<BatteryInformationDto> batteries) {
        Map<String, Integer> batteriesWithTimeToProduce = new HashMap<>();

        for (BatteryInformationDto b : batteries) {
            batteriesWithTimeToProduce.put(b.name, Integer.parseInt(b.time_to_produce));
        }

        this.batteryAssemblyLine.configureAssemblyLine(batteriesWithTimeToProduce);
    }
}
