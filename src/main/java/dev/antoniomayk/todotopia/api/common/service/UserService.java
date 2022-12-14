package dev.antoniomayk.todotopia.api.common.service;

import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.common.repository.UserRepository;
import dev.antoniomayk.todotopia.api.common.utils.UserUtils;
import dev.antoniomayk.todotopia.api.common.validator.UserValidator;
import dev.antoniomayk.todotopia.api.core.utils.BCryptUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

@Singleton
public class UserService {

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    @Inject
    public UserService(final UserRepository userRepository, final UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public Mono<User> save(final @NotNull User user) {
        return userValidator.validateNewUser(user)
                .then(Mono.just(user))
                .map(UserUtils::encryptUserPassword)
                .flatMap(userRepository::save);
    }

    @NotNull
    public Mono<Boolean> isAuthentic(final @NotNull String email, final @NotNull String plainTextPassword) {
        return userRepository.findByEmail(email)
                .flatMap(user -> Mono.just(BCryptUtils.verifyPassword(plainTextPassword, user.getPassword())))
                .defaultIfEmpty(Boolean.FALSE);
    }

}
