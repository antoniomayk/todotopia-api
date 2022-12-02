package dev.antoniomayk.todotopia.api.common.annotation;

import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;

import javax.validation.Constraint;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface PasswordNumbers {

    String message() default "{" + UserMessageCode.PASSWORD_NUMBERS + "}";

}
