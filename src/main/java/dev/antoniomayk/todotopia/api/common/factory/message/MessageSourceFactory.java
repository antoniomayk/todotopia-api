package dev.antoniomayk.todotopia.api.common.factory.message;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import jakarta.inject.Singleton;

@Factory
public class MessageSourceFactory {

    @Singleton
    public MessageSource exceptions() {
        return new ResourceBundleMessageSource("dev.antoniomayk.todotopia.api.common.exceptions");
    }

    @Singleton
    public MessageSource annotations() {
        return new ResourceBundleMessageSource("dev.antoniomayk.todotopia.api.common.annotations");
    }

}
