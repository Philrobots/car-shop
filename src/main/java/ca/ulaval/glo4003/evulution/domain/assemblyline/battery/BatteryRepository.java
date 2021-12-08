package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.List;

public interface BatteryRepository {

    void add(BatteryProduction batteryProduction);

    void remove(ProductionId productionId) throws InvalidMappingKeyException;

    List<BatteryProduction> getAndSendToProduction();

}
