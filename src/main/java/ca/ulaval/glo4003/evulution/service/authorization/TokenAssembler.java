package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;

public class TokenAssembler {
    public Token assembleTokenFromDto(TokenDto tokenDto) {
        return new Token(tokenDto.getToken());
    }

    public TokenDto assembleDtoFromToken(Token token) {
        return new TokenDto(token.getToken());
    }
}
