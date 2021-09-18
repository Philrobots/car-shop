package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.domain.token.Token;

public interface TokenRepository {
    Token loginCustomer(String email);
}
