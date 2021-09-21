package ca.ulaval.glo4003.evulution.api.authorization.dto;

public class TokenDtoAssembler {
    public TokenDto assembleFromString(String token){
        return new TokenDto(token);
    }
}
