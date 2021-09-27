package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.NotBlank;

public class ChooseBatteryDto extends GenericDto {
    @NotBlank
    public String type;
}
