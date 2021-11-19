package ca.ulaval.glo4003.evulution.domain.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TokenFactoryTest {

    private TokenFactory tokenFactory;

    @BeforeEach
    public void setUp() {
        this.tokenFactory = new TokenFactory();
    }

    @Test
    public void whenGenerateNewToken_thenATokenIsCreated() {
        // when
        Token token = tokenFactory.generateNewToken(true);

        // then
        assertNotNull(token);
    }
}
