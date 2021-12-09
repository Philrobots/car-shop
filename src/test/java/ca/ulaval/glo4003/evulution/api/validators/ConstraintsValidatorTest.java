package ca.ulaval.glo4003.evulution.api.validators;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerRequest;
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
    private static final String AN_INVALID_DATE = "2020-13-01";
    private static final String A_VALID_EMAIL = "email@email.com";
    private static final String A_VALID_PASSWORD = "valid_password";
    private static final String A_VALID_DATE = "2020-01-01";
    private static final String A_SEX = "M";
    private static final String A_NAME = "name";

    private ConstraintsValidator constraintsValidator;
    private CustomerRequest dto;

    @BeforeEach
    public void setup() {
        this.dto = new CustomerRequest();
        this.constraintsValidator = new ConstraintsValidator();
    }

    @Test
    public void givenDtoWithInvalidEmail_whenValidate_thenThrowBadInputParametersException() {
        // given
        this.dto.email = AN_INVALID_EMAIL;
        this.dto.password = A_VALID_PASSWORD;
        this.dto.birthdate = A_VALID_DATE;
        this.dto.sex = A_SEX;
        this.dto.name = A_NAME;

        // when
        Executable validate = () -> this.constraintsValidator.validate(dto);

        // then
        assertThrows(ServiceBadInputParameterException.class, validate);
    }

    @Test
    public void givenDtoWithInvalidDate_whenValidate_thenThrowBadInputParametersException() {
        // given
        this.dto.email = A_VALID_EMAIL;
        this.dto.password = A_VALID_PASSWORD;
        this.dto.birthdate = AN_INVALID_DATE;
        this.dto.sex = A_SEX;
        this.dto.name = A_NAME;

        // when
        Executable validate = () -> this.constraintsValidator.validate(dto);

        // then
        assertThrows(ServiceBadInputParameterException.class, validate);
    }

    @Test
    public void givenDtoWithValidParameters_whenValidate_thenShouldNotThrow() {
        // given
        this.dto.email = A_VALID_EMAIL;
        this.dto.password = A_VALID_PASSWORD;
        this.dto.birthdate = A_VALID_DATE;
        this.dto.sex = A_SEX;
        this.dto.name = A_NAME;

        // when
        this.constraintsValidator.validate(dto);
    }
}
