package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
