package ca.ulaval.glo4003.evulution.api.login.assembler;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginRequest;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;

public class LoginDtoAssembler {
    public LoginDto fromRequest(LoginRequest loginRequest) {
        LoginDto loginDto = new LoginDto();
        loginDto.email = loginRequest.email;
        loginDto.password = loginRequest.password;
        return loginDto;
    }
}
