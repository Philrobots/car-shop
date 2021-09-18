package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.Token;

public class TokenAssembler {

    public TokenDto toDto(Token token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.token = token.getToken();
        return tokenDto;
    }
}
