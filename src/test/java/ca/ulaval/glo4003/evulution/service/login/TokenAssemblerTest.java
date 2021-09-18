package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TokenAssemblerTest {

    private String A_RANDOM_STRING_TOKEN = "qwdfw6eggtf";

    @Mock
    private Token token;

    private TokenAssembler tokenAssembler;

    @BeforeEach
    private void setUp() {
        BDDMockito.given(token.getToken()).willReturn(A_RANDOM_STRING_TOKEN);
        tokenAssembler = new TokenAssembler();
    }

    @Test
    public void givenAnToken_whenToDto_thenShouldCallTheTokenToGetHisValue() {
        // when
        TokenDto dto = tokenAssembler.toDto(token);

        // then
        Mockito.verify(token).getToken();
    }

    @Test
    public void givenAnToken_whenToDto_thenShouldBeTheSameValue() {
        // when
        TokenDto dto = tokenAssembler.toDto(token);

        // then
        assertEquals(dto.token, A_RANDOM_STRING_TOKEN);
    }
}
