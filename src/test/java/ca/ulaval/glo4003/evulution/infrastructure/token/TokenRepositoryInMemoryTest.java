package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exceptions.UnauthorizedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryInMemoryTest {

    private static final String AN_EMAIL = "tiray@expat.com";

    @Mock
    private Token token;

    private TokenRepositoryInMemory tokenRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        tokenRepositoryInMemory = new TokenRepositoryInMemory();
    }

    @Test
    public void givenAnActiveEmail_whenGetEmail_thenShouldReturnCorrespondingEmail() {
        // given
        tokenRepositoryInMemory.addTokenWithEmail(token, AN_EMAIL);

        // when
        String email = tokenRepositoryInMemory.getEmail(token);

        // then
        assertEquals(email, AN_EMAIL);
    }

    @Test
    public void givenNoTokenForEmail_whenGetEmail_thenRepositoryDoesNotContainEmail() {
        // then
        assertNull(this.tokenRepositoryInMemory.getEmail(token));
    }

    @Test
    public void givenNoToken_whenValidateToken_thenThrowUnauthorizedRequestException() {
        // when
        Executable valideToken = () -> tokenRepositoryInMemory.validateToken(token);

        // then
        assertThrows(UnauthorizedRequestException.class, valideToken);
    }
}
