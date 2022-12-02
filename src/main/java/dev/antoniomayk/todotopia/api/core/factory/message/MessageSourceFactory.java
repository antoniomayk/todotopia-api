package dev.antoniomayk.todotopia.api.core.factory.message;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import jakarta.inject.Singleton;

@Factory
public class MessageSourceFactory {

    @Singleton
    public MessageSource exceptions() {
        return new ResourceBundleMessageSource("dev.antoniomayk.todotopia.api.core.exceptions");
    }

}
