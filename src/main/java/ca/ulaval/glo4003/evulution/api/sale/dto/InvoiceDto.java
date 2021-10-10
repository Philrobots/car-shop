package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.*;

public class InvoiceDto extends GenericDto {
    @NotNull
    @Pattern(regexp = "^\\d{3}$")
    public String bank_no;

    @NotNull
    @Pattern(regexp = "^\\d{7}$")
    public String account_no;

    @NotBlank
    public String frequency;
}
