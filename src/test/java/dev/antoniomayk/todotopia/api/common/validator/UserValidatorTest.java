package dev.antoniomayk.todotopia.api.common.validator;

import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.common.exception.*;
import dev.antoniomayk.todotopia.api.common.service.UserService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import reactor.test.StepVerifier;

@MicronautTest
class UserValidatorTest {

    @Inject
    UserValidator userValidator;

    @Inject
    UserService userService;

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserPasswordHasNoNumbers.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserPasswordHasNoNumbers(String password) {
        StepVerifier.create(userValidator.validatePasswordNumbers(password))
                .expectError(UserPasswordNumbersException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserPasswordHasNumbers.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserPasswordHasNumbers(String password) {
        StepVerifier.create(userValidator.validatePasswordNumbers(password))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserPasswordHasNoSymbols.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserPasswordHasNoSymbols(String password) {
        StepVerifier.create(userValidator.validatePasswordSymbols(password))
                .expectError(UserPasswordSymbolsException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserPasswordHasSymbols.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserPasswordHasSymbols(String password) {
        StepVerifier.create(userValidator.validatePasswordSymbols(password))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserPasswordHasNoLowercase.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserPasswordHasNoLowercase(String password) {
        StepVerifier.create(userValidator.validatePasswordLowercase(password))
                .expectError(UserPasswordLowercaseException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserPasswordHasLowercase.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserPasswordHasLowercase(String password) {
        StepVerifier.create(userValidator.validatePasswordLowercase(password))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserPasswordHasNoUppercase.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserPasswordHasNoUppercase(String password) {
        StepVerifier.create(userValidator.validatePasswordUppercase(password))
                .expectError(UserPasswordUppercaseException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserPasswordHasUppercase.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserPasswordHasUppercase(String password) {
        StepVerifier.create(userValidator.validatePasswordUppercase(password))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserPasswordIsNotBetween.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserPasswordIsNotBetween(String password) {
        StepVerifier.create(userValidator.validatePasswordLength(password))
                .expectError(UserPasswordLengthException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserPasswordIsBetween.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserPasswordIsBetween(String password) {
        StepVerifier.create(userValidator.validatePasswordLength(password))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserEmailAddressIsInvalid.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserEmailAddressIsInvalid(String emailAddress) {
        StepVerifier.create(userValidator.validateEmailAddress(emailAddress))
                .expectError(UserEmailInvalidException.class)
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserEmailAddressIsValid.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserEmailAddressIsValid(String emailAddress) {
        StepVerifier.create(userValidator.validatePasswordLength(emailAddress))
                .expectComplete()
                .verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldThrowExceptionWhenUserEmailAddressIsAlreadyInUse.csv", numLinesToSkip = 1)
    void shouldThrowExceptionWhenUserEmailAddressIsAlreadyInUse(String email1, String password1, String email2, String password2) {
        final var registeredUser = User.builder()
                .email(email1)
                .password(password1)
                .build();

        final var newUser = User.builder()
                .email(email2)
                .password(password2)
                .build();

        StepVerifier.create(userService.save(registeredUser))
                .consumeNextWith(unused -> StepVerifier.create(userValidator.validateEmailRegistration(newUser.getEmail()))
                        .verifyError(UserEmailRegisteredException.class))
                .verifyComplete();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldNotThrowExceptionWhenUserEmailAddressIsNotInUse.csv", numLinesToSkip = 1)
    void shouldNotThrowExceptionWhenUserEmailAddressIsNotInUse(String email1, String password1, String email2, String password2) {
        final var registeredUser = User.builder()
                .email(email1)
                .password(password1)
                .build();

        final var newUser = User.builder()
                .email(email2)
                .password(password2)
                .build();

        StepVerifier.create(userService.save(registeredUser))
                .consumeNextWith(unused -> StepVerifier.create(userValidator.validateEmailRegistration(newUser.getEmail()))
                        .verifyComplete())
                .verifyComplete();
    }

}
