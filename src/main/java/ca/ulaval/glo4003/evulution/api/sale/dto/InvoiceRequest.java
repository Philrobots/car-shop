package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class InvoiceRequest extends GenericRequest {
    @NotNull
    @Pattern(regexp = "^\\d{3}$")
    public String bank_no;

    @NotNull
    @Pattern(regexp = "^\\d{7}$")
    public String account_no;

    @NotBlank
    public String frequency;
}
