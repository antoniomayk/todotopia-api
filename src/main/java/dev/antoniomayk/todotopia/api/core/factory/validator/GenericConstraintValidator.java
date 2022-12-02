package dev.antoniomayk.todotopia.api.core.factory.validator;

import dev.antoniomayk.todotopia.api.core.annotation.EmailRFC5322;
import dev.antoniomayk.todotopia.api.core.utils.EmailUtils;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
public class GenericConstraintValidator {

    @Singleton
    public ConstraintValidator<EmailRFC5322, String> emailRFC5322() {
        return (text, annotationMetadata, context) -> EmailUtils.isValid(text);
    }

}
