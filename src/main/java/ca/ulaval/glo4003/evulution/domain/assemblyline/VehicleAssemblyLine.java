package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class VehicleAssemblyLine {

    private final VehicleAssemblyLineFacade vehicleAssemblyLineFacade;
    private final int timeOfWaitForOneWeek;

    public VehicleAssemblyLine(VehicleAssemblyLineFacade vehicleAssemblyLineFacade, int equivalenceOfOneWeekInSeconds) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
        this.timeOfWaitForOneWeek = equivalenceOfOneWeekInSeconds * 1000;

    }

    public void completeVehicleCommand(Sale sale) {
        this.sendVehicleToProduction(sale);
    }

    private void sendVehicleToProduction(Sale sale) {
        try {

            Car car = sale.getCar();
            TransactionId transactionId = sale.getTransactionId();
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
        } catch (InterruptedException e) {
        }
    }

}
