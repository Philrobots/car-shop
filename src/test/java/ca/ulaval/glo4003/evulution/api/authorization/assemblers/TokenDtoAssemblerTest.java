package ca.ulaval.glo4003.evulution.api.authorization.assemblers;

import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenDtoAssemblerTest {
    private static final String A_TOKEN = "abcd-1234";

    private TokenDtoAssembler tokenDtoAssembler;

    @BeforeEach
    public void setUp() {
        this.tokenDtoAssembler = new TokenDtoAssembler();
    }

    @Test
    public void whenAssembleFromString_thenCreatesTokenDto() {
        // when
        TokenDto tokenDto = this.tokenDtoAssembler.assembleFromString(A_TOKEN);

        // then
        assertNotNull(tokenDto);
    }
}
