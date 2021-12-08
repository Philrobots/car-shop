package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSeq;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;

public class AssemblyLineResources {

    private final BatteryAssemblyLineSequential batteryAssemblyLine;
    private final CarAssemblyLineSequential carAssemblyLine;
    private final CompleteAssemblyLineSeq completeAssemblyLine;
    private final AssemblyLineMediator assemblyLineMediator;

    public AssemblyLineResources(FactoryResources factoryResources, RepositoryResources repositoryResources) {

        BatteryAssemblyLineAdapter batteryAssemblyLineAdapter = new BatteryAssemblyLineAdapter(
                new BasicBatteryAssemblyLine(), JsonFileMapper.parseBatteries());

        CarAssemblyLineAdapter vehicleAssemblyLineAdapter = new CarAssemblyLineAdapter(new BasicVehicleAssemblyLine(),
                JsonFileMapper.parseModels());

        batteryAssemblyLine = new BatteryAssemblyLineSequential(batteryAssemblyLineAdapter,
                repositoryResources.getBatteryRepository(), factoryResources.getEmailFactory());

        carAssemblyLine = new CarAssemblyLineSequential(vehicleAssemblyLineAdapter, repositoryResources.getVehicleRepository(),
                factoryResources.getEmailFactory());

        completeAssemblyLine = new CompleteAssemblyLineSeq(factoryResources.getEmailFactory(),
                repositoryResources.getVehicleRepository(), repositoryResources.getBatteryRepository());

        assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine, completeAssemblyLine, carAssemblyLine);

        carAssemblyLine.setMediator(assemblyLineMediator);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
        completeAssemblyLine.setMediator(assemblyLineMediator);
    }

    public BatteryAssemblyLineSequential getBatteryAssemblyLine() {
        return batteryAssemblyLine;
    }

    public CarAssemblyLineSequential getCarAssemblyLine() {
        return carAssemblyLine;
    }

    public CompleteAssemblyLineSeq getCompleteAssemblyLine() {
        return completeAssemblyLine;
    }

    public AssemblyLineMediator getAssemblyLineMediator() {
        return assemblyLineMediator;
    }

}
