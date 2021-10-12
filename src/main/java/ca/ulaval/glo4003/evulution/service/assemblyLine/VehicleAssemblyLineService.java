package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLineFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleAssemblyLineService {

    private List<Sale> salesWaitList = new ArrayList<>();
    private boolean aVehicleIsBeingAssembled = false;
    private final VehicleAssemblyLineFacade vehicleAssemblyLineFacade;

    public VehicleAssemblyLineService(VehicleAssemblyLineFacade vehicleAssemblyLineFacade) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
        this.configureAssemblyLine();
    }

    public void completeVehicleCommand(Sale sale) {
        if (this.aVehicleIsBeingAssembled) {
            this.salesWaitList.add(sale);
        } else {
            this.sendVehicleToProduction(sale);
        }
    }

    private void sendVehicleToProduction(Sale sale) {
        try {
            this.aVehicleIsBeingAssembled = true;

            Car car = sale.getCar();
            TransactionId transactionId = sale.getTransactionId();
            String vehicleType = car.getName();

            // add command to vehicle assembly line
            this.vehicleAssemblyLineFacade.newCommand(transactionId, vehicleType);

            this.vehicleAssemblyLineFacade.advance();

            // jusqu'à temps que le car est pas assemble, on attend
            AssemblyStatus carStatus = this.vehicleAssemblyLineFacade.getStatus(transactionId);

            System.out.println(carStatus);

            while (carStatus == AssemblyStatus.IN_PROGRESS) {
                carStatus = this.vehicleAssemblyLineFacade.getStatus(transactionId);
                System.out.println(carStatus);
                Thread.sleep(3000);
            }

            System.out.print(carStatus);

            sale.completeSale();

            if (!this.salesWaitList.isEmpty()) {
                this.producesVehicleInWaitList();
            }

            this.aVehicleIsBeingAssembled = false;

        } catch (InterruptedException e) {
        }
    }

    private void producesVehicleInWaitList() {
        Sale sale = this.salesWaitList.get(0);
        this.sendVehicleToProduction(sale);
    }

    public void configureAssemblyLine() {
        // complète selon le fichier devis-vehicules.json qui est nul part
        Map<String, Integer> productionTimeByVehicleType = new HashMap<>();
        productionTimeByVehicleType.put("Vandry", 10); // temporaire
        this.vehicleAssemblyLineFacade.configureAssemblyLine(productionTimeByVehicleType);
    }

}
