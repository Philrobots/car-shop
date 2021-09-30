package ca.ulaval.glo4003.evulution.api.delivery.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.NotBlank;

public class DeliveryLocationDto extends GenericDto {
    @NotBlank
    public String mode;
    @NotBlank
    public String location;
}
