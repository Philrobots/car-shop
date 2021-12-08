package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TokenAssemblerTest {
    private static final String A_RANDOM_STRING_TOKEN = "qwdfw6eggtf";

    @Mock
    private Token token;

    @Mock
    private TokenDto tokenDto;

    private TokenAssembler tokenAssembler;

    @BeforeEach
    public void setUp() {
        tokenAssembler = new TokenAssembler();
    }

    @Test
    public void givenAToken_whenAssembleDtoFromToken_thenShouldCreateTokenDto() {
        // given
        BDDMockito.given(token.getToken()).willReturn(A_RANDOM_STRING_TOKEN);

        // when
        TokenDto dto = tokenAssembler.assembleDtoFromToken(token);

        // then
        assertNotNull(dto);
    }

    @Test
    public void givenATokenDto_whenAssembleTokenFromDto_thenShouldCreateToken() {
        // given
        BDDMockito.given(tokenDto.getToken()).willReturn(A_RANDOM_STRING_TOKEN);

        // when
        Token token = tokenAssembler.assembleTokenFromDto(tokenDto);

        // then
        assertNotNull(token);
    }
}
