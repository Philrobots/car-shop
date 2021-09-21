package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.Token;

public class TokenAssembler {
    public TokenDto tokenToDto(Token token) {
        return new TokenDto(token.getToken());
    }

    public Token dtoToToken(TokenDto tokenDto) {
        return new Token(tokenDto.getToken());
    }
}
