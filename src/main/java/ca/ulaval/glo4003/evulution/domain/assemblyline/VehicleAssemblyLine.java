package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;

import java.util.LinkedList;

public class VehicleAssemblyLine {
    private final VehicleAssemblyAdapter vehicleAssemblyAdapter;
    private final LinkedList<VehicleProduction> vehicleProductionWaitList = new LinkedList<>();
    private final VehicleRepository vehicleRepository;
    private boolean isCarInProduction = false;
    private AssemblyLineMediator assemblyLineMediator;
    private VehicleProduction currentVehicleProduction;

    public VehicleAssemblyLine(VehicleAssemblyAdapter vehicleAssemblyAdapter, VehicleRepository vehicleRepository) {
        this.vehicleAssemblyAdapter = vehicleAssemblyAdapter;
        this.vehicleRepository = vehicleRepository;
    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    public void addProduction(VehicleProduction vehicleProduction) {
        if (isCarInProduction) {
            this.vehicleProductionWaitList.add(vehicleProduction);
        } else {
            this.currentVehicleProduction = vehicleProduction;
            this.isCarInProduction = true;
            this.vehicleAssemblyAdapter.newVehicleCommand(vehicleProduction.getTransactionId(),
                    vehicleProduction.getName());
        }
    }

    public void advance() {
        if (!isCarInProduction)
            return;

        this.vehicleAssemblyAdapter.advance();

        AssemblyStatus carStatus = this.vehicleAssemblyAdapter
                .getStatus(this.currentVehicleProduction.getTransactionId());

        if (carStatus == AssemblyStatus.ASSEMBLED) {
            this.vehicleRepository.add(currentVehicleProduction.getName(), currentVehicleProduction);
            this.assemblyLineMediator.notify(this.getClass());

            if (this.vehicleProductionWaitList.isEmpty()) {
                this.isCarInProduction = false;
            } else {
                this.currentVehicleProduction = this.vehicleProductionWaitList.pop();
                this.vehicleAssemblyAdapter.newVehicleCommand(currentVehicleProduction.getTransactionId(),
                        currentVehicleProduction.getName());
            }
        }
    }
}
