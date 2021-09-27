package ca.ulaval.glo4003.evulution.api.login.dto;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import jakarta.validation.constraints.NotBlank;

public class LoginDto extends GenericDto {
    @NotBlank
    public String email;

    @NotBlank
    public String password;
}
