package ca.ulaval.glo4003.evulution.domain.production;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;

public class ProductionAssembler {

    public VehicleProduction assembleVehicleProductionFromSale(Sale sale) {
        return new VehicleProduction(sale.getTransactionId(), sale.getCarName());
    }

    public BatteryProduction assembleBatteryProductionFromSale(Sale sale) {
        return new BatteryProduction(sale.getTransactionId(), sale.getBatteryType());
    }
}
