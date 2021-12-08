package ca.ulaval.glo4003.evulution.api.validators;

import ca.ulaval.glo4003.evulution.api.GenericRequest;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
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

    public void validate(GenericRequest dto) {
        if (dto == null)
            throw new ServiceBadInputParameterException();
        Set<ConstraintViolation<GenericRequest>> violations = validator.validate(dto);
        if (!violations.isEmpty())
            throw new ServiceBadInputParameterException();
    }

}
