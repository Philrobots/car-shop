package ca.ulaval.glo4003.evulution.api.login.dto;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest extends GenericRequest {
    @NotBlank
    public String email;

    @NotBlank
    public String password;
}
