package ca.ulaval.glo4003.evulution.api.validators;

import ca.ulaval.glo4003.evulution.api.GenericDto;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;

import java.util.Set;

public class ConstraintsValidator {
    private final Validator validator;

    public ConstraintsValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .buildValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public void validate(GenericDto dto) {
        Set<ConstraintViolation<GenericDto>> violations = validator.validate(dto);
        if (!violations.isEmpty())
            throw new BadInputParameterException();
    }

}
