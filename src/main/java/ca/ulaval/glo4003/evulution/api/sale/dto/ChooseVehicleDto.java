package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.NotBlank;

public class ChooseVehicleDto extends GenericDto {
    @NotBlank
    public String name;

    @NotBlank
    public String color;
}
