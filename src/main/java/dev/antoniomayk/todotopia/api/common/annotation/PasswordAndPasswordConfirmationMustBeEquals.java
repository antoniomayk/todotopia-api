package dev.antoniomayk.todotopia.api.common.annotation;

import dev.antoniomayk.todotopia.api.common.constant.UserDTOMessageCode;

import javax.validation.Constraint;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface PasswordAndPasswordConfirmationMustBeEquals {

    String message() default "{" + UserDTOMessageCode.PASSWORD_AND_PASSWORD_CONFIRMATION_MUST_BE_EQUALS + "}";

}
