package ca.ulaval.glo4003.evulution.api.customer.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerDto extends GenericDto {
    @NotBlank
    public String name;

    @NotBlank
    public String birthdate;

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String sex;
}
