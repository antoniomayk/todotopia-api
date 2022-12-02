package dev.antoniomayk.todotopia.api.common.exception.handler;

import dev.antoniomayk.todotopia.api.common.exception.UserPasswordLengthException;
import dev.antoniomayk.todotopia.api.core.exception.handler.ServerErrorExceptionHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.HashMap;

@Produces
@Singleton
@Requires(classes = {UserPasswordLengthException.class, ExceptionHandler.class})
public class UserPasswordLengthExceptionHandler implements ExceptionHandler<UserPasswordLengthException, HttpResponse<?>> {

    private final ServerErrorExceptionHandler serverErrorExceptionHandler;

    @Inject
    public UserPasswordLengthExceptionHandler(final ServerErrorExceptionHandler serverErrorExceptionHandler) {
        this.serverErrorExceptionHandler = serverErrorExceptionHandler;
    }

    @Override
    public HttpResponse<?> handle(final HttpRequest request, final UserPasswordLengthException exception) {
        final var variables = new HashMap<String, Object>();
        variables.put("max", exception.getMax());
        variables.put("min", exception.getMin());
        return serverErrorExceptionHandler.errorResponseProcessor(request, exception, variables);
    }

}
