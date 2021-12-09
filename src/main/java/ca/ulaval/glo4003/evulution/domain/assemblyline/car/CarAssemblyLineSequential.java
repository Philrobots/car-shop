package ca.ulaval.glo4003.evulution.domain.assemblyline.car;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

import java.util.LinkedList;
import java.util.List;

public class CarAssemblyLineSequential implements CarAssemblyLine {

    private LinkedList<CarProduction> carProductionWaitList = new LinkedList<>();

    private final CarAssemblyAdapter carAssemblyAdapter;
    private final CarProductionRepository carProductionRepository;
    private final EmailFactory emailFactory;
    private AssemblyLineMediator assemblyLineMediator;
    private CarProduction currentCarProduction;
    private boolean isBatteryInFire = false;
    private boolean isCarInProduction = false;

    public CarAssemblyLineSequential(CarAssemblyAdapter carAssemblyAdapter,
            CarProductionRepository carProductionRepository, EmailFactory emailFactory) {
        this.carAssemblyAdapter = carAssemblyAdapter;
        this.carProductionRepository = carProductionRepository;
        this.emailFactory = emailFactory;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    public void addProduction(CarProduction carProduction) throws EmailException {
        this.carProductionWaitList.add(carProduction);
        if (!(isCarInProduction || this.isBatteryInFire)) {
            setupNextProduction();
        }
    }

    public void advance() throws EmailException {
        if (!isCarInProduction || this.isBatteryInFire) {
            return;
        }

        boolean isCarAssembled = currentCarProduction.advance(carAssemblyAdapter);

        if (isCarAssembled) {
            this.carProductionRepository.add(currentCarProduction);
            this.assemblyLineMediator.notify(CarAssemblyLine.class);

            if (this.carProductionWaitList.isEmpty()) {
                this.isCarInProduction = false;
            } else {
                setupNextProduction();
            }
        }
    }

    public void shutdown() {
        this.isBatteryInFire = true;
    }

    public void reactivate() throws EmailException {
        this.isBatteryInFire = false;
        if (this.assemblyLineMediator.shouldCarReactivateProduction() && !carProductionWaitList.isEmpty())
            setupNextProduction();
    }

    public void startNext() throws EmailException {
        if (!carProductionWaitList.isEmpty())
            setupNextProduction();
    }

    @Override
    public void transferWaitingList(CarAssemblyLine carAssemblyLine) {
        this.carProductionWaitList = new LinkedList<>(carAssemblyLine.getWaitingList());
    }

    @Override
    public List<CarProduction> getWaitingList() {
        if (isCarInProduction) this.carProductionWaitList.add(currentCarProduction);
        LinkedList<CarProduction> returnList = new LinkedList<>(this.carProductionWaitList);
        carProductionWaitList.clear();

        return returnList;
    }

    private void setupNextProduction() throws EmailException {
        this.isCarInProduction = true;
        this.currentCarProduction = this.carProductionWaitList.pop();
        this.currentCarProduction.sendEmail(emailFactory);
        this.currentCarProduction.newCarCommand(carAssemblyAdapter);
    }
}
