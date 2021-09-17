package ca.ulaval.glo4003.ws.infrastructure.token;

import ca.ulaval.glo4003.ws.domain.token.Token;
import ca.ulaval.glo4003.ws.domain.token.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryInMemoryTest {

    private String AN_EMAIL = "tiray@expat.com";

    @Mock
    private Token token;

    @Mock
    private TokenFactory tokenFactory;

    private TokenRepositoryInMemory tokenRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        BDDMockito.given(tokenFactory.generateNewToken()).willReturn(token);
        tokenRepositoryInMemory = new TokenRepositoryInMemory(tokenFactory);
    }

    @Test
    public void givenAnEmail_thenAddLoginCustomer_thenShouldCallTheTokenFactoryToGenerateToken() {
        // when
        tokenRepositoryInMemory.loginCustomer(AN_EMAIL);

        // then
        Mockito.verify(tokenFactory).generateNewToken();
    }

    @Test
    public void givenAnEmail_thenGetAllToken_shouldHaveAnTokenLinkToTheEmail() {
        // given
        tokenRepositoryInMemory.loginCustomer(AN_EMAIL);

        // then
        Map<String, Token> tokens = tokenRepositoryInMemory.getTokens();
        Token aToken = tokens.get(AN_EMAIL);

        // should
        assertTrue(aToken == token);
    }

    @Test
    public void givenAnEmail_thenSignOutTheCustomer_thenShouldReturnAMapWithNoToken() {
        // given
        tokenRepositoryInMemory.loginCustomer(AN_EMAIL);

        // then
        tokenRepositoryInMemory.signOutCustomer(AN_EMAIL);

        // should
        Map<String, Token> tokens = tokenRepositoryInMemory.getTokens();
        assertTrue(tokens.isEmpty());
    }

}
