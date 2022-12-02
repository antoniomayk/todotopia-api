package dev.antoniomayk.todotopia.api.common.exception.handler;

import dev.antoniomayk.todotopia.api.common.exception.UserEmailRegisteredException;
import dev.antoniomayk.todotopia.api.core.exception.handler.ServerErrorExceptionHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UserEmailRegisteredException.class, ExceptionHandler.class})
public class UserEmailRegisteredExceptionHandler implements ExceptionHandler<UserEmailRegisteredException, HttpResponse<?>> {

    private final ServerErrorExceptionHandler serverErrorExceptionHandler;

    @Inject
    public UserEmailRegisteredExceptionHandler(final ServerErrorExceptionHandler serverErrorExceptionHandler) {
        this.serverErrorExceptionHandler = serverErrorExceptionHandler;
    }

    @Override
    public HttpResponse<?> handle(final HttpRequest request, final UserEmailRegisteredException exception) {
        return serverErrorExceptionHandler.errorResponseProcessor(request, exception, null);
    }

}
