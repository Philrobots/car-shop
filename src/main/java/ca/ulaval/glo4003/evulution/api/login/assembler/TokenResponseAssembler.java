package ca.ulaval.glo4003.evulution.api.login.assembler;

import ca.ulaval.glo4003.evulution.api.login.dto.TokenResponse;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;

public class TokenResponseAssembler {
    public TokenResponse toResponse(TokenDto tokenDto) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.token = tokenDto.getToken();
        return tokenResponse;
    }
}
