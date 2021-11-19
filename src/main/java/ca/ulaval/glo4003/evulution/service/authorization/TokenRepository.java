package ca.ulaval.glo4003.evulution.service.authorization;

import ca.ulaval.glo4003.evulution.domain.token.Token;

public interface TokenRepository {

    void addTokenWithEmail(Token token, String email);

    String getEmail(Token token);

    void validateToken(Token token);

    void validateAdminToken(Token token);
}
