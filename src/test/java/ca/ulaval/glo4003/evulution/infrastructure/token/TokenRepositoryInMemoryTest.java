package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryInMemoryTest {

    private static final String AN_EMAIL = "tiray@expat.com";

    @Mock
    private Token token;

    @Mock
    private AccountId accountId;

    private TokenRepositoryInMemory tokenRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        tokenRepositoryInMemory = new TokenRepositoryInMemory();
    }

    @Test
    public void givenAnActiveEmail_whenGetAccountId_thenShouldReturnCorrespondingEmail() throws TokenNotFoundException {
        // given
        tokenRepositoryInMemory.addToken(token, this.accountId);

        // when
        AccountId accountId = tokenRepositoryInMemory.getAccountId(token);

        // then
        assertEquals(accountId, this.accountId);
    }

    @Test
    public void givenNoTokenForEmail_whenGetAccountId_thenThrowsTokenNotFoundException() {
        // when
        Executable getAccountId = () -> this.tokenRepositoryInMemory.getAccountId(token);

        // then
        assertThrows(TokenNotFoundException.class, getAccountId);
    }
}
