package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleRepository;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.HashMap;
import java.util.Map;

public class VehicleRepositoryInMemory implements VehicleRepository {
    private final Map<String, VehicleProduction> vehicles = new HashMap<>();

    @Override
    public void add(String name, VehicleProduction vehicleProduction) {
        this.vehicles.put(name, vehicleProduction);
    }

    @Override
    public void remove(String name) {
        VehicleProduction vehicleProduction = this.vehicles.remove(name);
        if (vehicleProduction == null) {
            throw new InvalidMappingKeyException();
        }
    }
}
