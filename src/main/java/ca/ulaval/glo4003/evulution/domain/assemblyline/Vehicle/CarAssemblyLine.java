package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.CarProduction;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

import java.util.LinkedList;

public class CarAssemblyLine {
    private final LinkedList<CarProduction> carProductionWaitList = new LinkedList<>();

    private final CarAssemblyAdapter carAssemblyAdapter;
    private final VehicleRepository vehicleRepository;
    private final EmailFactory emailFactory;
    private AssemblyLineMediator assemblyLineMediator;
    private CarProduction currentCarProduction;
    private boolean isBatteryInFire = false;
    private boolean isCarInProduction = false;

    public CarAssemblyLine(CarAssemblyAdapter carAssemblyAdapter, VehicleRepository vehicleRepository,
            EmailFactory emailFactory) {
        this.carAssemblyAdapter = carAssemblyAdapter;
        this.vehicleRepository = vehicleRepository;
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

        AssemblyStatus carStatus = currentCarProduction.advance(carAssemblyAdapter);

        if (carStatus == AssemblyStatus.ASSEMBLED) {
            this.vehicleRepository.add(currentCarProduction);
            this.assemblyLineMediator.notify(this.getClass());

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
        if (assemblyLineMediator.getState() == AssemblyState.CAR && !carProductionWaitList.isEmpty())
            setupNextProduction();
    }

    public void startNext() throws EmailException {
        if (!carProductionWaitList.isEmpty())
            setupNextProduction();
    }

    private void setupNextProduction() throws EmailException {
        this.isCarInProduction = true;
        this.currentCarProduction = this.carProductionWaitList.pop();
        this.currentCarProduction.sendEmail(emailFactory);
        this.currentCarProduction.newCarCommand(carAssemblyAdapter);
    }
}
