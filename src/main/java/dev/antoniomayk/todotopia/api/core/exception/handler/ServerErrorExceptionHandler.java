package dev.antoniomayk.todotopia.api.core.exception.handler;

import dev.antoniomayk.todotopia.api.core.exception.ServerErrorException;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Singleton
public class ServerErrorExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerErrorExceptionHandler.class);

    private final ErrorResponseProcessor<JsonError> errorResponseProcessor;

    private final MessageSource messageSource;

    @Inject
    public ServerErrorExceptionHandler(final ErrorResponseProcessor<JsonError> errorResponseProcessor, final MessageSource messageSource) {
        this.errorResponseProcessor = errorResponseProcessor;
        this.messageSource = messageSource;
    }

    @NotNull
    public MutableHttpResponse<JsonError> errorResponseProcessor(@NonNull final HttpRequest<?> request, @NonNull final ServerErrorException exception, @Nullable final Map<String, Object> variables) {
        LOGGER.error(exception.getMessage(), exception);

        final var messageCode = exception.getMessageCode();
        final var userLocale = request.getLocale().orElse(Locale.getDefault());
        final var message = Objects.nonNull(variables)
                ? messageSource.getMessage(messageCode, userLocale, variables)
                : messageSource.getMessage(messageCode, userLocale);
        return errorResponseProcessor.processResponse(
                ErrorContext.builder(request)
                        .cause(exception)
                        .errorMessage(message.orElseThrow())
                        .build(),
                HttpResponse.serverError());
    }

}
