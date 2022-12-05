package dev.antoniomayk.todotopia.api.security.controller;

import dev.antoniomayk.todotopia.api.common.dto.UserDTO;
import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.security.service.AuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@Controller
public class AuthController {

    private final AuthService authService;

    @Inject
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @Post("/sign-up")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<HttpResponse<UserDTO>> signUp(@Body @Valid Mono<UserDTO> userDTOMono) {
        return userDTOMono
                .map(UserDTO::toEntity)
                .flatMap(authService::signUp)
                .map(User::toDTO)
                .map(HttpResponse::created);
    }

}
