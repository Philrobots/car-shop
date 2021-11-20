package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyState;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;

import java.util.LinkedList;
import java.util.List;

public class VehicleAssemblyLine {
    private final LinkedList<VehicleProduction> vehicleProductionWaitList = new LinkedList<>();

    private final VehicleAssemblyAdapter vehicleAssemblyAdapter;
    private final VehicleRepository vehicleRepository;
    private AssemblyLineMediator assemblyLineMediator;
    private VehicleProduction currentVehicleProduction;
    private final EmailFactory emailFactory;

    private boolean isBatteryInFire = false;
    private boolean isCarInProduction = false;

    public VehicleAssemblyLine(VehicleAssemblyAdapter vehicleAssemblyAdapter, VehicleRepository vehicleRepository,
            EmailFactory emailFactory) {
        this.vehicleAssemblyAdapter = vehicleAssemblyAdapter;
        this.vehicleRepository = vehicleRepository;
        this.emailFactory = emailFactory;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    public void addProduction(VehicleProduction vehicleProduction) {
        this.vehicleProductionWaitList.add(vehicleProduction);
        if (!(isCarInProduction || this.isBatteryInFire)) {
            setupNextProduction();
        }
    }

    public void advance() {
        if (!isCarInProduction || this.isBatteryInFire) {
            System.out.println("VehicleAssemblyLine skipped");
            return;
        }

        if (!this.currentVehicleProduction.isEmailSent()) {
            emailFactory.createVehicleBuiltEmail(List.of(this.currentVehicleProduction.getEmail()),
                this.currentVehicleProduction.getProductionTimeInWeeks()).send();
            this.currentVehicleProduction.setEmailSent(true);
        }

        System.out.println("VehicleAssemblyLine advance with: " + this.currentVehicleProduction.getName());

        this.vehicleAssemblyAdapter.advance();

        AssemblyStatus carStatus = this.vehicleAssemblyAdapter
                .getStatus(this.currentVehicleProduction.getTransactionId());

        if (carStatus == AssemblyStatus.ASSEMBLED) {
            this.vehicleRepository.add(currentVehicleProduction.getName(), currentVehicleProduction);
            this.assemblyLineMediator.notify(this.getClass());

            if (this.vehicleProductionWaitList.isEmpty()) {
                this.isCarInProduction = false;
            } else {
                setupNextProduction();
            }
        }
    }

    public void shutdown() {
        this.isBatteryInFire = true;
    }

    public void reactivate() {
        this.isBatteryInFire = false;
        if (assemblyLineMediator.getState() == AssemblyState.CAR && !vehicleProductionWaitList.isEmpty())
            setupNextProduction();
    }

    public void startNext() {
        if (!vehicleProductionWaitList.isEmpty())
            setupNextProduction();
    }

    private void setupNextProduction() {
        this.isCarInProduction = true;
        this.currentVehicleProduction = this.vehicleProductionWaitList.pop();
        this.vehicleAssemblyAdapter.newVehicleCommand(currentVehicleProduction.getTransactionId(),
                currentVehicleProduction.getName());
    }
}
