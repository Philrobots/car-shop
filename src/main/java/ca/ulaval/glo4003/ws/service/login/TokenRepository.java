package ca.ulaval.glo4003.ws.service.login;

import ca.ulaval.glo4003.ws.domain.token.Token;

public interface TokenRepository {
    Token loginCustomer(String email);
}
