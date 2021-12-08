package ca.ulaval.glo4003.evulution.domain.assemblyline.switchProduction;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;

public class SwitchProduction {
    private String lineType;
    private String modeProduction;

    public SwitchProduction(String lineType, String modeProduction) {
        this.lineType = lineType;
        this.modeProduction = modeProduction;
    }

    public boolean shouldGoSequential(ProductionType currentProductionType) {
        return currentProductionType.equals(ProductionType.JUST_IN_TIME);
    }

}
