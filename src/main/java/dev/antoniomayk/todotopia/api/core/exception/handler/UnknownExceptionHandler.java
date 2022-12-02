package dev.antoniomayk.todotopia.api.core.exception.handler;

import dev.antoniomayk.todotopia.api.core.constant.CoreExceptionMessageCode;
import io.micronaut.context.MessageSource;
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

import java.util.Locale;

@Produces
@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class UnknownExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownExceptionHandler.class);

    private final ErrorResponseProcessor<?> errorResponseProcessor;

    private final MessageSource messageSource;

    @Inject
    public UnknownExceptionHandler(final ErrorResponseProcessor<?> errorResponseProcessor, final MessageSource messageSource) {
        this.errorResponseProcessor = errorResponseProcessor;
        this.messageSource = messageSource;
    }

    @Override
    public HttpResponse<?> handle(final HttpRequest request, final Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        return errorResponseProcessor.processResponse(
                ErrorContext.builder(request)
                        .cause(exception)
                        .errorMessage(getMessage(request))
                        .build(),
                HttpResponse.serverError());
    }

    @NotNull
    private String getMessage(final HttpRequest<?> request) {
        final var locale = request.getLocale().orElse(Locale.getDefault());
        return messageSource.getMessage(CoreExceptionMessageCode.JAVA_EXCEPTION, locale).orElseThrow();
    }

}
