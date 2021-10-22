package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

import java.util.LinkedList;

public class VehicleAssemblyLine {

    private AssemblyLineMediator assemblyLineMediator;
    private final VehicleAssemblyAdapter vehicleAssemblyLineFacade;
    private final LinkedList<VehicleProduction> vehicleProductionWaitList = new LinkedList<>();
    private boolean isCarInProduction = false;
    private VehicleProduction currentVehicleProduction;

    public VehicleAssemblyLine(VehicleAssemblyAdapter vehicleAssemblyLineFacade) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
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
            this.vehicleAssemblyLineFacade.newVehicleCommand(vehicleProduction.getTransactionId(),
                    vehicleProduction.getName());
        }
    }

    public void advance() {
        if (!isCarInProduction)
            return;

        this.vehicleAssemblyLineFacade.advance();

        AssemblyStatus carStatus = this.vehicleAssemblyLineFacade
                .getStatus(this.currentVehicleProduction.getTransactionId());

        if (carStatus == AssemblyStatus.ASSEMBLED) {
            this.assemblyLineMediator.notify(this.getClass());
            System.out.println("VEHICLE ASSEMBLED");

            if (this.vehicleProductionWaitList.isEmpty()) {
                this.isCarInProduction = false;
            } else {
                this.currentVehicleProduction = this.vehicleProductionWaitList.pop();
                this.vehicleAssemblyLineFacade.newVehicleCommand(currentVehicleProduction.getTransactionId(),
                        currentVehicleProduction.getName());
            }
        }
    }

    // public void completeVehicleCommand(TransactionId transactionId, Car car) {
    // try {
    // String vehicleType = car.getName();
    //
    // this.vehicleAssemblyLineFacade.newVehicleCommand(transactionId, vehicleType);
    //
    // boolean isCarAssembled = false;
    //
    // while (!isCarAssembled) {
    // AssemblyStatus carStatus = this.vehicleAssemblyLineFacade.getStatus(transactionId);
    // if (carStatus != AssemblyStatus.ASSEMBLED) {
    // this.vehicleAssemblyLineFacade.advance();
    // } else {
    // isCarAssembled = true;
    // }
    //
    // Thread.sleep(timeOfWaitForOneWeek);
    // }
    // car.setCarAsAssembled();
    // } catch (InterruptedException e) {
    // throw new VehicleAssemblyException();
    // }
    // }
}
