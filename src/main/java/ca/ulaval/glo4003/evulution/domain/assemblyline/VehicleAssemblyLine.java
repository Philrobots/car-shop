package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.VehicleAssemblyException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediator;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.LinkedList;

public class VehicleAssemblyLine {

    private AssemblyLineMediator assemblyLineMediator;
    private final VehicleAssemblyAdapter vehicleAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;
    private final LinkedList<Sale> carWaitList = new LinkedList<>();
    private boolean isCarInProduction = false;
    private Sale currentSale;

    public VehicleAssemblyLine(VehicleAssemblyAdapter vehicleAssemblyLineFacade, int equivalenceOfOneWeekInSeconds) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;

    }

    public void setMediator(AssemblyLineMediator assemblyLineMediator) {
        this.assemblyLineMediator = assemblyLineMediator;
    }

    public void addCommand(Sale sale) {
        if (isCarInProduction) {
            this.carWaitList.add(sale);
        } else {
            this.currentSale = sale;
            this.isCarInProduction = true;
            this.vehicleAssemblyLineFacade.newVehicleCommand(sale.getTransactionId(), sale.getCar().getName());
        }
    }

    public void advance() {
        if (!isCarInProduction)
            return;

        this.vehicleAssemblyLineFacade.advance();

        AssemblyStatus carStatus = this.vehicleAssemblyLineFacade.getStatus(this.currentSale.getTransactionId());

        if (carStatus == AssemblyStatus.ASSEMBLED) {
            this.assemblyLineMediator.notify(this.getClass());

            if (this.carWaitList.isEmpty()) {
                this.isCarInProduction = false;
            } else {
                this.currentSale = this.carWaitList.pop();
                this.vehicleAssemblyLineFacade.newVehicleCommand(currentSale.getTransactionId(),
                        currentSale.getCar().getName());
            }
        }

    }

    public void completeVehicleCommand(TransactionId transactionId, Car car) {
        try {
            String vehicleType = car.getName();

            this.vehicleAssemblyLineFacade.newVehicleCommand(transactionId, vehicleType);

            boolean isCarAssembled = false;

            while (!isCarAssembled) {
                AssemblyStatus carStatus = this.vehicleAssemblyLineFacade.getStatus(transactionId);
                if (carStatus != AssemblyStatus.ASSEMBLED) {
                    this.vehicleAssemblyLineFacade.advance();
                } else {
                    isCarAssembled = true;
                }

                Thread.sleep(timeOfWaitForOneWeek);
            }
            car.setCarAsAssembled();
        } catch (InterruptedException e) {
            throw new VehicleAssemblyException();
        }
    }
}
