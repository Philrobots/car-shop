package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.ActiveTokenValidator;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;

public class AuthorizationService {
    private final TokenAssembler tokenAssembler;
    private final ActiveTokenValidator activeTokenValidator;

    public AuthorizationService(TokenAssembler tokenAssembler, ActiveTokenValidator activeTokenValidator) {
        this.tokenAssembler = tokenAssembler;
        this.activeTokenValidator = activeTokenValidator;
    }

    public void ValidateToken(TokenDto tokenDto) throws UnauthorizedRequestException {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.activeTokenValidator.validateToken(token);
    }
}
