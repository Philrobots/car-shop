package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.CarAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;

public class AssemblyLineResources {

    private final BatteryAssemblyLine batteryAssemblyLine;
    private final CarAssemblyLine carAssemblyLine;
    private final CompleteAssemblyLine completeAssemblyLine;
    private final AssemblyLineMediator assemblyLineMediator;

    public AssemblyLineResources(FactoryResources factoryResources, RepositoryResources repositoryResources) {

        BatteryAssemblyLineAdapter batteryAssemblyLineAdapter = new BatteryAssemblyLineAdapter(
                new BasicBatteryAssemblyLine(), JsonFileMapper.parseBatteries());

        CarAssemblyLineAdapter vehicleAssemblyLineAdapter = new CarAssemblyLineAdapter(new BasicVehicleAssemblyLine(),
                JsonFileMapper.parseModels());

        batteryAssemblyLine = new BatteryAssemblyLine(batteryAssemblyLineAdapter,
                repositoryResources.getBatteryRepository(), factoryResources.getEmailFactory());

        carAssemblyLine = new CarAssemblyLine(vehicleAssemblyLineAdapter, repositoryResources.getVehicleRepository(),
                factoryResources.getEmailFactory());

        completeAssemblyLine = new CompleteAssemblyLine(factoryResources.getEmailFactory(),
                repositoryResources.getVehicleRepository(), repositoryResources.getBatteryRepository());

        assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine, completeAssemblyLine, carAssemblyLine);

        carAssemblyLine.setMediator(assemblyLineMediator);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
        completeAssemblyLine.setMediator(assemblyLineMediator);
    }

    public BatteryAssemblyLine getBatteryAssemblyLine() {
        return batteryAssemblyLine;
    }

    public CarAssemblyLine getCarAssemblyLine() {
        return carAssemblyLine;
    }

    public CompleteAssemblyLine getCompleteAssemblyLine() {
        return completeAssemblyLine;
    }

    public AssemblyLineMediator getAssemblyLineMediator() {
        return assemblyLineMediator;
    }

}
