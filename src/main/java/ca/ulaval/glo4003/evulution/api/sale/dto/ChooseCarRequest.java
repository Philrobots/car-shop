package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.NotBlank;

public class ChooseCarRequest extends GenericRequest {
    @NotBlank
    public String name;

    @NotBlank
    public String color;
}
