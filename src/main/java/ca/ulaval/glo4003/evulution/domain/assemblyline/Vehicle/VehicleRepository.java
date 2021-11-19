package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;

public interface VehicleRepository {

    void add(String name, VehicleProduction vehicleProduction);

    void remove(String name);
}
