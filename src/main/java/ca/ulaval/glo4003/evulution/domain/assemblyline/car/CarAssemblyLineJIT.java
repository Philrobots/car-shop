package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionWithoutManufacture;

import java.util.LinkedList;
import java.util.List;

public class CarAssemblyLineJIT implements CarAssemblyLine {
    private LinkedList<CarProductionAssociatedWithManufacture> carWaitingList = new LinkedList<>();
    private boolean isCarInProduction = false;
    private CarProduction currentCarInProduction;
    private AssemblyLineMediator assemblyLineMediator;
    private CarAssemblyAdapter carAssemblyAdapter;
    private CarProductionRepository carProductionRepository;
    private CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;

    @Override
    public void addProduction(CarProductionAssociatedWithManufacture carProduction) throws EmailException {
        boolean hasCarBeenReplaced = carProductionRepository.replaceCarProductionWithoutManufactureIfItHasBeenMade(carProduction);
        if (hasCarBeenReplaced) {
            assemblyLineMediator.notify(CarAssemblyLine.class);
        } else {
            carWaitingList.add(carProduction);
        }
    }

    @Override
    public void advance() throws EmailException {
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

    }

    @Override
    public void reactivate() throws EmailException {

    }

    @Override
    public void startNext() throws EmailException {

    }

    @Override
    public void transferWaitingList(CarAssemblyLine carAssemblyLine) {

    }

    @Override
    public List<CarProductionAssociatedWithManufacture> getWaitingList() {
//        if (isCarInProduction) this.carProductionWaitList.add(currentCarProduction);
        LinkedList<CarProductionAssociatedWithManufacture> returnList = new LinkedList<>(this.carWaitingList);
        carWaitingList.clear();

        return returnList;
    }
}
