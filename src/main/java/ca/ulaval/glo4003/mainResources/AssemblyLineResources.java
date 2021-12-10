package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.car_manufacture.BasicBatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.car_manufacture.BasicVehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter.BatteryAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineJIT;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineJITTypeSelector;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyLineAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.complete.CompleteAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorImpl;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorSwitcher;
import ca.ulaval.glo4003.evulution.domain.email.ProductionLineEmailNotifier;
import ca.ulaval.glo4003.evulution.infrastructure.mappers.JsonFileMapper;

public class AssemblyLineResources {

    private final BatteryAssemblyLineSequential batteryAssemblyLine;
    private final CarAssemblyLineSequential carAssemblyLineSequential;
    private final CompleteAssemblyLineSequential completeAssemblyLine;
    private final AssemblyLineMediatorImpl assemblyLineMediator;
    private final CarAssemblyLineJIT carAssemblyLineJIT;
    private final CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;
    private final ProductionLineEmailNotifier productionLineEmailNotifier;

    public AssemblyLineResources(FactoryResources factoryResources, RepositoryResources repositoryResources) {

        productionLineEmailNotifier = new ProductionLineEmailNotifier(factoryResources.getEmailFactory());

        BatteryAssemblyLineAdapter batteryAssemblyLineAdapter = new BatteryAssemblyLineAdapter(
                new BasicBatteryAssemblyLine(), JsonFileMapper.parseBatteries());

        CarAssemblyLineAdapter vehicleAssemblyLineAdapter = new CarAssemblyLineAdapter(new BasicVehicleAssemblyLine(),
                JsonFileMapper.parseModels());

        batteryAssemblyLine = new BatteryAssemblyLineSequential(batteryAssemblyLineAdapter,
                repositoryResources.getBatteryRepository(), factoryResources.getEmailFactory(),
                productionLineEmailNotifier);

        carAssemblyLineSequential = new CarAssemblyLineSequential(vehicleAssemblyLineAdapter,
                repositoryResources.getVehicleRepository(), productionLineEmailNotifier);

        this.carAssemblyLineJITTypeSelector = new CarAssemblyLineJITTypeSelector(
                factoryResources.getCarProductionFactory());

        carAssemblyLineJIT = new CarAssemblyLineJIT(this.getAssemblyLineMediator(), vehicleAssemblyLineAdapter,
                repositoryResources.getVehicleRepository(), this.carAssemblyLineJITTypeSelector);

        completeAssemblyLine = new CompleteAssemblyLineSequential(factoryResources.getEmailFactory(),
                repositoryResources.getVehicleRepository(), repositoryResources.getBatteryRepository(),
                productionLineEmailNotifier);

        assemblyLineMediator = new AssemblyLineMediatorImpl(batteryAssemblyLine, completeAssemblyLine,
                carAssemblyLineSequential);

        carAssemblyLineSequential.setMediator(assemblyLineMediator);
        batteryAssemblyLine.setMediator(assemblyLineMediator);
        completeAssemblyLine.setMediator(assemblyLineMediator);
        carAssemblyLineJIT.setMediator(assemblyLineMediator);
    }

    public AssemblyLineMediatorSwitcher getMediatorSwitcher() {
        return this.assemblyLineMediator;
    }

    public CarAssemblyLine getCarAssemblyLineJIT() {
        return this.carAssemblyLineJIT;
    }

    public BatteryAssemblyLineSequential getBatteryAssemblyLine() {
        return batteryAssemblyLine;
    }

    public CarAssemblyLine getCarAssemblyLineSequential() {
        return this.carAssemblyLineSequential;
    }

    public CompleteAssemblyLineSequential getCompleteAssemblyLine() {
        return completeAssemblyLine;
    }

    public AssemblyLineMediator getAssemblyLineMediator() {
        return assemblyLineMediator;
    }

    public ProductionLineEmailNotifier getProductionLineEmailNotifier() {
        return this.productionLineEmailNotifier;
    }

}
