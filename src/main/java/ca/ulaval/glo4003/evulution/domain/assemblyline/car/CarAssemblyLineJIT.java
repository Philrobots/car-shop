package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionWithoutManufacture;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;

import java.util.LinkedList;
import java.util.List;

public class CarAssemblyLineJIT implements CarAssemblyLine {
    private LinkedList<CarProduction> carWaitingList = new LinkedList<>();
    private boolean isCarInProduction = false;
    private boolean isBatteryInFire = false;
    private CarProduction currentCarInProduction;
    private AssemblyLineMediator assemblyLineMediator;
    private CarAssemblyAdapter carAssemblyAdapter;
    private CarProductionRepository carProductionRepository;
    private CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;

    public CarAssemblyLineJIT(AssemblyLineMediator assemblyLineMediator, CarAssemblyAdapter carAssemblyAdapter, CarProductionRepository carProductionRepository, CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector) {
        this.assemblyLineMediator = assemblyLineMediator;
        this.carAssemblyAdapter = carAssemblyAdapter;
        this.carProductionRepository = carProductionRepository;
        this.carAssemblyLineJITTypeSelector = carAssemblyLineJITTypeSelector;
    }

    @Override
    public void addProduction(CarProduction carProduction) throws CarNotAssociatedWithManufactureException {
        boolean hasCarBeenReplaced = carProductionRepository.replaceCarProductionWithoutManufactureIfItHasBeenMade(carProduction);
        if (hasCarBeenReplaced) {
            assemblyLineMediator.notify(CarAssemblyLine.class);
        } else {
            carWaitingList.add(carProduction);
        }
    }

    @Override
    public void advance() {
        if (isBatteryInFire) return;
        if (!isCarInProduction) currentCarInProduction = carAssemblyLineJITTypeSelector.getNextCarProduction();

        boolean carFinished = currentCarInProduction.advance(carAssemblyAdapter);

        if (carFinished){
            carProductionRepository.add(currentCarInProduction);
            if (currentCarInProduction.isAssociatedWithManufacture()) assemblyLineMediator.notify(CarAssemblyLine.class);

            if (!carWaitingList.isEmpty()){
               currentCarInProduction = carWaitingList.pop();
            } else{
                isCarInProduction = false;
            }
        }
    }

    @Override
    public void shutdown() {
        isBatteryInFire = true;
    }

    @Override
    public void reactivate() {
        isBatteryInFire = false;
    }

    @Override
    public void startNext() {

    }

    @Override
    public void transferWaitingList(CarAssemblyLine carAssemblyLine) {
        carWaitingList = new LinkedList<>(carAssemblyLine.getWaitingList());
    }

    @Override
    public List<CarProduction> getWaitingList() {
        if (isCarInProduction) carWaitingList.add(currentCarInProduction);
        LinkedList<CarProduction> returnList = new LinkedList<>(this.carWaitingList);
        carWaitingList.clear();

        return returnList;
    }
}
