package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CarAssemblyLineRepository;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLineFacade;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VehicleAssemblyLineService {

    private List<Sale> salesWaitList = new ArrayList<>();
    private boolean aVehicleIsBeingAssembled = false;
    private final VehicleAssemblyLineFacade vehicleAssemblyLineFacade;
    private final CarAssemblyLineRepository carAssemblyLineRepository;

    public VehicleAssemblyLineService(VehicleAssemblyLineFacade vehicleAssemblyLineFacade, CarAssemblyLineRepository carAssemblyLineRepository) {
        this.vehicleAssemblyLineFacade = vehicleAssemblyLineFacade;
        this.carAssemblyLineRepository = carAssemblyLineRepository;
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
        this.salesWaitList.remove(0);
    }

    public void configureAssemblyLine() {
        Map<String, Integer> productionTimeByVehicleType = this.carAssemblyLineRepository.getCarTimeToProduce();
        this.vehicleAssemblyLineFacade.configureAssemblyLine(productionTimeByVehicleType);
    }

}
