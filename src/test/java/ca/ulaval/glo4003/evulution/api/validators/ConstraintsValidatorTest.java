package ca.ulaval.glo4003.evulution.api.validators;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginRequest;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ConstraintsValidatorTest {
    private static final String AN_INVALID_EMAIL = "invalid_email";
    private static final String A_VALID_PASSWORD = "valid_password";

    private ConstraintsValidator constraintsValidator;
    private LoginRequest dto;

    @BeforeEach
    public void setup() {
        this.dto = new LoginRequest();
        this.constraintsValidator = new ConstraintsValidator();
    }
    /*
     * @Test public void givenDtoWithInvalidConstraints_whenValidate_thenThrowBadInputParametersException() { // given
     * this.dto.email = AN_INVALID_EMAIL; this.dto.password = A_VALID_PASSWORD;
     * 
     * // when Executable validate = () -> this.constraintsValidator.validate(dto);
     * 
     * // then assertThrows(ServiceBadInputParameterException.class, validate); }
     */
}
