package ca.ulaval.glo4003.ws.service.login;

import ca.ulaval.glo4003.ws.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.ws.domain.token.Token;

public class TokenAssembler {

    public TokenDto toDto(Token token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.token = token.getToken();
        return tokenDto;
    }
}
