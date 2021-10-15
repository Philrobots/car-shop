package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Timer;
import java.util.TimerTask;

public class VehicleAssemblyLine {

    private final VehicleAssemblyLineFacade vehicleAssemblyLineFacade;

    public VehicleAssemblyLine(VehicleAssemblyLineFacade vehicleAssemblyLineFacade) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
    }

    public void completeVehicleCommand(Sale sale) {
        // 3. Injecter la variable d'environnement (.env)
        this.sendVehicleToProduction(sale);
    }

    private void sendVehicleToProduction(Sale sale) {
        try {

            Car car = sale.getCar();
            TransactionId transactionId = sale.getTransactionId();
            String vehicleType = car.getName();

            // add command to vehicle assembly line
            this.vehicleAssemblyLineFacade.newCommand(transactionId, vehicleType);
            this.vehicleAssemblyLineFacade.advance();

            // jusqu'à temps que le car est pas assemble, on attend
            boolean isCarAssembled = false;


            while (isCarAssembled) {
                AssemblyStatus carStatus = this.vehicleAssemblyLineFacade.getStatus(transactionId);

                if (carStatus != AssemblyStatus.ASSEMBLED) {
                    this.vehicleAssemblyLineFacade.advance();
                } else {
                    isCarAssembled = true;
                }

                Thread.sleep(3000);
            }

            sale.completeSale();

        } catch (InterruptedException e) {
        }
    }

}
