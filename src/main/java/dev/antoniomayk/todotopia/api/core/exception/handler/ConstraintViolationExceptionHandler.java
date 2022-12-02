package dev.antoniomayk.todotopia.api.core.exception.handler;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Produces
@Singleton
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
public class ConstraintViolationExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

    private final ErrorResponseProcessor<?> errorResponseProcessor;

    private final MessageSource messageSource;

    @Inject
    public ConstraintViolationExceptionHandler(final ErrorResponseProcessor<?> errorResponseProcessor, final MessageSource messageSource) {
        this.errorResponseProcessor = errorResponseProcessor;
        this.messageSource = messageSource;
    }

    @Override
    public HttpResponse<?> handle(HttpRequest request, ConstraintViolationException exception) {
        LOGGER.error(exception.getMessage(), exception);

        return errorResponseProcessor.processResponse(
                ErrorContext
                        .builder(request)
                        .cause(exception)
                        .errorMessages(exception.getConstraintViolations()
                                .stream()
                                .map(getMessage(request))
                                .toList())
                        .build(),
                HttpResponse.badRequest());
    }

    @NotNull
    private Function<ConstraintViolation<?>, String> getMessage(final HttpRequest<?> request) {
        return constraintViolation -> {
            final var fieldName = StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), Boolean.TRUE)
                    .reduce((node, node2) -> node2)
                    .orElseThrow()
                    .getName();
            final var variables = constraintViolation.getConstraintDescriptor().getAttributes();
            variables.put("fieldName", fieldName);
            final var locale = request.getLocale().orElse(Locale.getDefault());
            return messageSource.getMessage(constraintViolation.getMessage(), locale, variables).orElseThrow();
        };
    }

}
