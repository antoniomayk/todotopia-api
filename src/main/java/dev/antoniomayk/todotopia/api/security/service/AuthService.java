package dev.antoniomayk.todotopia.api.security.service;

import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.common.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class AuthService {

    private final UserService userService;

    @Inject
    public AuthService(final UserService userService) {
        this.userService = userService;
    }

    public Mono<User> signUp(User user) {
        return Mono.just(user)
                .flatMap(userService::save);
    }

}
