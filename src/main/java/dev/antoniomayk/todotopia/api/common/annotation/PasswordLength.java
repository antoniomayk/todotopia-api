package dev.antoniomayk.todotopia.api.common.annotation;

import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;

import javax.validation.Constraint;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface PasswordLength {

    String message() default "{" + UserMessageCode.PASSWORD_LENGTH + "}";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

}
