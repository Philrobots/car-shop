package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.VehicleAssemblyException;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class VehicleAssemblyLine {

    private final VehicleAssemblyAdapter vehicleAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;

    public VehicleAssemblyLine(VehicleAssemblyAdapter vehicleAssemblyLineFacade, int equivalenceOfOneWeekInSeconds) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;

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
