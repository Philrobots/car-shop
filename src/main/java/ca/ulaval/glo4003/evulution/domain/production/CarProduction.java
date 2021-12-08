package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.CarAssemblyAdapter;
import ca.ulaval.glo4003.evulution.domain.email.EmailFactory;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

import java.util.List;

public class CarProduction {

    private ProductionId productionId;
    private String email;
    private int carProductionTimeInWeeks;
    private String carType;

    public CarProduction(ProductionId productionId, String carType, String email, int carProductionTime) {
        this.productionId = productionId;
        this.carType = carType;
        this.email = email;
        this.carProductionTimeInWeeks = carProductionTime;
    }

    public void newCarCommand(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.newVehicleCommand(productionId, carType);
    }

    public void sendEmail(EmailFactory emailFactory) throws EmailException {
        emailFactory.createBatteryBatteryInProductionEmail(List.of(email), carProductionTimeInWeeks).send();
    }

    public AssemblyStatus advance(CarAssemblyAdapter carAssemblyAdapter) {
        carAssemblyAdapter.advance();
        return carAssemblyAdapter.getStatus(productionId);
    }

    public ProductionId getProductionId() {
        return productionId;
    }
}
