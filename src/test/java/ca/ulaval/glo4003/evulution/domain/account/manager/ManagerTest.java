package ca.ulaval.glo4003.evulution.domain.account.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagerTest {
    private static final String AN_EMAIL = "kevin@expat.com";
    private static final String A_PASSWORD = "Fireball";

    private Manager manager;

    @BeforeEach
    public void setUp() {
        this.manager = new Manager(AN_EMAIL, A_PASSWORD);
    }

    @Test
    public void givenAnInvalidPassword_whenIsAuthenticationValid_thenShouldReturnInvalid() {
        // given
        String invalidPassword = "12FireBallSvp";

        // when
        boolean isAuthenticationValid = manager.isAuthenticationValid(AN_EMAIL, invalidPassword);

        // given
        assertFalse(isAuthenticationValid);
    }

    @Test
    public void whenIsAuthenticationValid_thenShouldReturnValid() {
        // when
        boolean isAuthenticationValid = manager.isAuthenticationValid(AN_EMAIL, A_PASSWORD);

        // given
        assertTrue(isAuthenticationValid);
    }
}
