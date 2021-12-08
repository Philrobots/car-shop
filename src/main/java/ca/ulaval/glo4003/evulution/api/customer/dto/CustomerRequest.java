package ca.ulaval.glo4003.evulution.api.customer.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerRequest extends GenericRequest {
    @NotBlank
    public String name;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @NotNull
    public String birthdate;

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String sex;
}
