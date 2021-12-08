package ca.ulaval.glo4003.evulution.api.delivery.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.NotBlank;

public class DeliveryLocationRequest extends GenericRequest {
    @NotBlank
    public String mode;
    @NotBlank
    public String location;
}
