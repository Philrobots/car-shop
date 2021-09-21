package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;

public class AuthorizationService {
    private TokenAssembler tokenAssembler;
    private TokenRepository tokenRepository;

    public AuthorizationService(TokenAssembler tokenAssembler, TokenRepository tokenRepository) {
        this.tokenAssembler = tokenAssembler;
        this.tokenRepository = tokenRepository;
    }

    public void ValidateToken(TokenDto tokenDto) throws UnauthorizedRequestException {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        String email = this.tokenRepository.getEmail(token);
        if (email == null) {
            throw new UnauthorizedRequestException();
        }

    }
}
