package dev.antoniomayk.todotopia.api.security.service;

import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.common.service.UserService;
import dev.antoniomayk.todotopia.api.security.constant.SecurityExceptionMessageCode;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Singleton
public class AuthService {

    private final MessageSource messageSource;

    private final UserService userService;

    @Inject
    public AuthService(final MessageSource messageSource, final UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public Mono<User> signUp(User user) {
        return Mono.just(user)
                .flatMap(userService::save);
    }

    public Flux<AuthenticationResponse> signIn(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.from(userService.isAuthentic((String) authenticationRequest.getIdentity(), (String) authenticationRequest.getSecret())
                .handle((aBoolean, authenticationResponseSynchronousSink) -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        authenticationResponseSynchronousSink.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity()));
                        authenticationResponseSynchronousSink.complete();
                    } else {
                        final var locale = httpRequest.getLocale().orElse(Locale.getDefault());
                        final var message = messageSource.getMessage(SecurityExceptionMessageCode.EMAIL_OR_PASSWORD_INCORRECT, locale).orElseThrow();
                        authenticationResponseSynchronousSink.error(AuthenticationResponse.exception(message));
                    }
                }));
    }

}
