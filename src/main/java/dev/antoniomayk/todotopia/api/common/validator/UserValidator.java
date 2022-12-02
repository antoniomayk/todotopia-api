package dev.antoniomayk.todotopia.api.common.validator;

import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.common.exception.*;
import dev.antoniomayk.todotopia.api.common.repository.UserRepository;
import dev.antoniomayk.todotopia.api.core.utils.EmailUtils;
import dev.antoniomayk.todotopia.api.core.utils.StringUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Singleton
public class UserValidator {

    private final UserRepository userRepository;

    @Inject
    public UserValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NotNull
    public Mono<Void> validatePasswordUppercase(final @Nullable String password) {
        return Mono.justOrEmpty(password)
                .handle((text, synchronousSink) -> {
                    if (Boolean.TRUE.equals(StringUtils.hasUppercase(text))) synchronousSink.complete();
                    else synchronousSink.error(new UserPasswordUppercaseException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validatePasswordLowercase(final @Nullable String password) {
        return Mono.justOrEmpty(password)
                .handle((text, synchronousSink) -> {
                    if (Boolean.TRUE.equals(StringUtils.hasLowercase(text))) synchronousSink.complete();
                    else synchronousSink.error(new UserPasswordLowercaseException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validatePasswordNumbers(final @Nullable String password) {
        return Mono.justOrEmpty(password)
                .handle((text, synchronousSink) -> {
                    if (Boolean.TRUE.equals(StringUtils.hasNumbers(text))) synchronousSink.complete();
                    else synchronousSink.error(new UserPasswordNumbersException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validatePasswordSymbols(final @Nullable String password) {
        return Mono.justOrEmpty(password)
                .handle((text, synchronousSink) -> {
                    if (Boolean.TRUE.equals(StringUtils.hasSymbols(text))) synchronousSink.complete();
                    else synchronousSink.error(new UserPasswordSymbolsException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validatePasswordLength(final @Nullable String password) {
        return Mono.justOrEmpty(password)
                .handle((text, synchronousSink) -> {
                    final var min = 8;
                    final var max = 64;
                    if (Boolean.TRUE.equals(StringUtils.isBetween(text, min, max))) synchronousSink.complete();
                    else synchronousSink.error(new UserPasswordLengthException(min, max));
                })
                .then();
    }

    @NotNull
    public Mono<Void> validateEmailAddress(final @Nullable String email) {
        return Mono.justOrEmpty(email)
                .handle((text, synchronousSink) -> {
                    if (Boolean.TRUE.equals(EmailUtils.isValid(text))) synchronousSink.complete();
                    else synchronousSink.error(new UserEmailInvalidException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validatePassword(final @Nullable String password) {
        return Mono.when(
                        validatePasswordLength(password),
                        validatePasswordSymbols(password),
                        validatePasswordNumbers(password),
                        validatePasswordLowercase(password),
                        validatePasswordUppercase(password))
                .subscribeOn(Schedulers.parallel());
    }

    @NotNull
    public Mono<Void> validateEmail(final @Nullable String email) {
        return Mono.when(
                        validateEmailAddress(email),
                        validateEmailRegistration(email))
                .subscribeOn(Schedulers.single());
    }

    @NotNull
    public Mono<Void> validateEmailRegistration(final @Nullable String email) {
        return userRepository.findByEmail(email)
                .hasElement()
                .handle((aBoolean, synchronousSink) -> {
                    if (Boolean.FALSE.equals(aBoolean)) synchronousSink.complete();
                    else synchronousSink.error(new UserEmailRegisteredException());
                })
                .then();
    }

    @NotNull
    public Mono<Void> validateNewUser(final @NonNull User user) {
        return Mono.when(
                        validateEmail(user.getEmail()),
                        validatePassword(user.getPassword()))
                .subscribeOn(Schedulers.parallel());
    }

}
