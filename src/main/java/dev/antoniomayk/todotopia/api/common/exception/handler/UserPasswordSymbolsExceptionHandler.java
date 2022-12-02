package dev.antoniomayk.todotopia.api.common.exception.handler;

import dev.antoniomayk.todotopia.api.common.exception.UserPasswordSymbolsException;
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
@Requires(classes = {UserPasswordSymbolsException.class, ExceptionHandler.class})
public class UserPasswordSymbolsExceptionHandler implements ExceptionHandler<UserPasswordSymbolsException, HttpResponse<?>> {

    private final ServerErrorExceptionHandler serverErrorExceptionHandler;

    @Inject
    public UserPasswordSymbolsExceptionHandler(final ServerErrorExceptionHandler serverErrorExceptionHandler) {
        this.serverErrorExceptionHandler = serverErrorExceptionHandler;
    }

    @Override
    public HttpResponse<?> handle(final HttpRequest request, final UserPasswordSymbolsException exception) {
        return serverErrorExceptionHandler.errorResponseProcessor(request, exception, null);
    }

}
