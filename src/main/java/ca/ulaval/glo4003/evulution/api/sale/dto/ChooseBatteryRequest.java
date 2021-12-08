package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.NotBlank;

public class ChooseBatteryRequest extends GenericRequest {
    @NotBlank
    public String type;
}
