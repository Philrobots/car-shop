package ca.ulaval.glo4003.evulution.domain.token;

import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

public class ActiveTokenValidator {
    private final TokenRepository tokenRepository;

    public ActiveTokenValidator(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void validateToken(Token token) {
        String email = this.tokenRepository.getEmail(token);
        if (email == null) {
            throw new UnauthorizedRequestException();
        }
    }
}
