package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.car.ModelInformationDto;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

import java.util.List;

public class CarProductionFactory {
    private static String COMPACT_CAR_TYPE = "Compact";
    private Integer COMPACT_TIME_TO_PRODUCE;
    private static String SUBCOMPACT_CAR_TYPE = "Subcompact";
    private Integer SUBCOMPACT_TIME_TO_PRODUCE;
    private static String LUXURY_CAR_TYPE = "Luxury";
    private Integer LUXURY_TIME_TO_PRODUCE;

    public CarProductionFactory(List<ModelInformationDto> modelMapperDtos) {
        for (ModelInformationDto modelInformationDto : modelMapperDtos){
            if (modelInformationDto.style.equals(COMPACT_CAR_TYPE)) COMPACT_TIME_TO_PRODUCE = Integer.parseInt(modelInformationDto.time_to_produce);
            if (modelInformationDto.style.equals(SUBCOMPACT_CAR_TYPE)) SUBCOMPACT_TIME_TO_PRODUCE = Integer.parseInt(modelInformationDto.time_to_produce);
            if (modelInformationDto.style.equals(LUXURY_CAR_TYPE)) LUXURY_TIME_TO_PRODUCE = Integer.parseInt(modelInformationDto.time_to_produce);
        }
    }

    public CarProduction create(ProductionId productionId, String carStyle, String email, int carProductionTime) {
        return new CarProductionAssociatedWithManufacture(productionId, carStyle, email, carProductionTime);
    }
    public CarProduction createCompact() {
        return new CarProductionWithoutManufacture(new ProductionId(), COMPACT_CAR_TYPE, COMPACT_TIME_TO_PRODUCE);
    }
    public CarProduction createSubCompact() {
        return new CarProductionWithoutManufacture(new ProductionId(), SUBCOMPACT_CAR_TYPE, SUBCOMPACT_TIME_TO_PRODUCE);
    }
    public CarProduction createLuxury() {
        return new CarProductionWithoutManufacture(new ProductionId(), LUXURY_CAR_TYPE, LUXURY_TIME_TO_PRODUCE);
    }
}
