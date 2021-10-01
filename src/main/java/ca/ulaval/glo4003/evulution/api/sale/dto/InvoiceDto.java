package ca.ulaval.glo4003.evulution.api.sale.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InvoiceDto extends GenericDto {
    @NotNull
    @Min(0)
    @Max(999)
    public Integer bank_no;

    @NotNull
    @Min(0)
    @Max(9999999)
    public Integer account_no;

    @NotBlank
    public String frequency;
}
