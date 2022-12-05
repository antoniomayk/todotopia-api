package dev.antoniomayk.todotopia.api.security.provider;

import dev.antoniomayk.todotopia.api.security.service.AuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Singleton
public class AuthenticationProviderEmailPassword implements AuthenticationProvider {

    private final AuthService authService;

    @Inject
    public AuthenticationProviderEmailPassword(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Flux<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return authService.signIn(httpRequest, authenticationRequest);
    }

}
