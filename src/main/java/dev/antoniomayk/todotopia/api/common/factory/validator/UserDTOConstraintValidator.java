package dev.antoniomayk.todotopia.api.common.factory.validator;

import dev.antoniomayk.todotopia.api.common.annotation.*;
import dev.antoniomayk.todotopia.api.common.dto.UserDTO;
import dev.antoniomayk.todotopia.api.core.utils.StringUtils;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.Objects;

@Factory
public class UserDTOConstraintValidator {

    @Singleton
    public ConstraintValidator<PasswordLength, String> passwordLength() {
        return (text, annotationMetadata, context) -> {
            final var min = annotationMetadata.get("min", Integer.class, 0);
            final var max = annotationMetadata.get("max", Integer.class, Integer.MAX_VALUE);
            return Objects.nonNull(text) && StringUtils.isBetween(text, min, max);
        };
    }

    @Singleton
    public ConstraintValidator<PasswordSymbols, String> passwordSymbols() {
        return (text, annotationMetadata, context) -> StringUtils.hasSymbols(text);
    }

    @Singleton
    public ConstraintValidator<PasswordNumbers, String> passwordNumbers() {
        return (text, annotationMetadata, context) -> StringUtils.hasNumbers(text);
    }

    @Singleton
    public ConstraintValidator<PasswordLowercase, String> passwordLowercase() {
        return (text, annotationMetadata, context) -> StringUtils.hasLowercase(text);
    }

    @Singleton
    public ConstraintValidator<PasswordUppercase, String> passwordUppercase() {
        return (text, annotationMetadata, context) -> StringUtils.hasUppercase(text);
    }

    @Singleton
    public ConstraintValidator<PasswordAndPasswordConfirmationMustBeEquals, UserDTO> passwordAndPasswordConfirmationMustBeEquals() {
        return (userDTO, annotationMetadata, context) -> Objects.nonNull(userDTO) && StringUtils.equals(userDTO.getPassword(), userDTO.getPasswordConfirmation());
    }

}
