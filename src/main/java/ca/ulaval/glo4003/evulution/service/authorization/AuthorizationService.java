package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;

public class AuthorizationService {
    private final TokenAssembler tokenAssembler;
    private final TokenRepository tokenRepository;

    public AuthorizationService(TokenAssembler tokenAssembler, TokenRepository tokenRepository) {
        this.tokenAssembler = tokenAssembler;
        this.tokenRepository = tokenRepository;
    }

    public void ValidateToken(TokenDto tokenDto) throws UnauthorizedRequestException {
        Token token = this.tokenAssembler.dtoToToken(tokenDto);
        this.tokenRepository.validateToken(token);
    }
}
