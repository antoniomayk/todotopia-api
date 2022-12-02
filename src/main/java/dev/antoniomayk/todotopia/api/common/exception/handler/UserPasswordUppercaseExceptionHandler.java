package dev.antoniomayk.todotopia.api.common.exception.handler;

import dev.antoniomayk.todotopia.api.common.exception.UserPasswordUppercaseException;
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
@Requires(classes = {UserPasswordUppercaseException.class, ExceptionHandler.class})
public class UserPasswordUppercaseExceptionHandler implements ExceptionHandler<UserPasswordUppercaseException, HttpResponse<?>> {

    private final ServerErrorExceptionHandler serverErrorExceptionHandler;

    @Inject
    public UserPasswordUppercaseExceptionHandler(final ServerErrorExceptionHandler serverErrorExceptionHandler) {
        this.serverErrorExceptionHandler = serverErrorExceptionHandler;
    }

    @Override
    public HttpResponse<?> handle(final HttpRequest request, final UserPasswordUppercaseException exception) {
        return serverErrorExceptionHandler.errorResponseProcessor(request, exception, null);
    }

}
