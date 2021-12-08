package ca.ulaval.glo4003.evulution.api.authorization.assemblers;

import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;

public class TokenDtoAssembler {
    public TokenDto assembleFromString(String token) {
        return new TokenDto(token);
    }
}
