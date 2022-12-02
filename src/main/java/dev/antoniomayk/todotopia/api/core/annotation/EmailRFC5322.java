package dev.antoniomayk.todotopia.api.core.annotation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface EmailRFC5322 {

    String message() default "{dev.antoniomayk.todotopia.api.core.annotation.EmailRFC5322.message}";

}
