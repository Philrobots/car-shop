package ca.ulaval.glo4003.evulution.service.assemblyLine.dto;

public class SwitchProductionsDto {
    public String lineType;
    public String productionMode;

    public SwitchProductionsDto(String lineType, String productionMode) {
        this.lineType = lineType;
        this.productionMode = productionMode;
    }
}
