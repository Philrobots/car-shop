package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;

import java.util.LinkedList;
import java.util.List;

public class CarAssemblyLineJIT implements CarAssemblyLine {
    private LinkedList<CarProduction> carWaitingList = new LinkedList<>();
    private boolean isCarInProduction = false;
    private boolean isBatteryInFire = false;
    private int notifyBatteryCounter = 0;
    private CarProduction currentCarInProduction;
    private AssemblyLineMediator assemblyLineMediator;
    private CarAssemblyAdapter carAssemblyAdapter;
    private CarProductionRepository carProductionRepository;
    private CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector;

    public CarAssemblyLineJIT(AssemblyLineMediator assemblyLineMediator, CarAssemblyAdapter carAssemblyAdapter,
            CarProductionRepository carProductionRepository,
            CarAssemblyLineJITTypeSelector carAssemblyLineJITTypeSelector) {
        this.assemblyLineMediator = assemblyLineMediator;
        this.carAssemblyAdapter = carAssemblyAdapter;
        this.carProductionRepository = carProductionRepository;
        this.carAssemblyLineJITTypeSelector = carAssemblyLineJITTypeSelector;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    @Override
    public void addProduction(CarProduction carProduction) throws CarNotAssociatedWithManufactureException {
        boolean hasCarBeenReplaced = this.carProductionRepository
                .replaceCarProductionWithoutManufactureIfItHasBeenMade(carProduction);
        if (hasCarBeenReplaced) {
            this.notifyBatteryCounter++;
        } else {
            this.carWaitingList.add(carProduction);
        }
    }

    @Override
    public void advance() {

        if (this.isBatteryInFire) {
            System.out.println("Skipping Car assembly");
            return;
        }

        if (this.notifyBatteryCounter > 0) {
            if (this.assemblyLineMediator.isCarState()){
                this.assemblyLineMediator.notify(CarAssemblyLine.class);
                this.notifyBatteryCounter--;

            }

        }

        if (!this.isCarInProduction) {
            System.out.println("No car in production, picking a new Car");
            this.currentCarInProduction = this.carAssemblyLineJITTypeSelector.getNextCarProduction();
            this.currentCarInProduction.newCarCommand(this.carAssemblyAdapter);
            this.isCarInProduction = true;
        }

        boolean carFinished = currentCarInProduction.advance(carAssemblyAdapter);

        System.out.println("Building car Just in time");

        if (carFinished) {
            System.out.println("Car is finished just in time");

            this.carProductionRepository.add(this.currentCarInProduction);

            if (this.currentCarInProduction.isAssociatedWithManufacture()) {
                this.notifyBatteryCounter++;
            }

            if (!this.carWaitingList.isEmpty()) {
                this.currentCarInProduction = this.carWaitingList.pop();
                System.out.println("Picking new car in waiting list just in time");
                this.currentCarInProduction.newCarCommand(this.carAssemblyAdapter);
            } else {
                this.isCarInProduction = false;
            }
        }
    }

    @Override
    public void shutdown() {
        this.isBatteryInFire = true;
    }

    @Override
    public void reactivate() {
        this.isBatteryInFire = false;
    }

    @Override
    public void startNext() {

    }

    @Override
    public void transferAssemblyLine(CarAssemblyLine carAssemblyLine) {
        this.carWaitingList = new LinkedList<>(carAssemblyLine.getWaitingList());
        this.isBatteryInFire = carAssemblyLine.getIsBatteryInFire();
        if (!this.carWaitingList.isEmpty()) {
            this.currentCarInProduction = this.carWaitingList.pop();
            this.currentCarInProduction.newCarCommand(this.carAssemblyAdapter);
            this.isCarInProduction = true;
        }
    }

    @Override
    public List<CarProduction> getWaitingList() {
        if (this.isCarInProduction && this.currentCarInProduction.isAssociatedWithManufacture())
            this.carWaitingList.addFirst(this.currentCarInProduction);
        LinkedList<CarProduction> returnList = new LinkedList<>(this.carWaitingList);
        this.carWaitingList.clear();
        this.isCarInProduction = false;

        return returnList;
    }

    @Override
    public boolean getIsBatteryInFire() {
        return isBatteryInFire;
    }
}
