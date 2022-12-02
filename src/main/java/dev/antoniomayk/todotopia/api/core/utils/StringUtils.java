package dev.antoniomayk.todotopia.api.core.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    public Boolean hasSymbols(@Nullable String text) {
        return Pattern.compile("^(?=.*[`~!@#$%^&*()_\\-+={}|\\\\:;\"'<,>.?/]).*$")
                .matcher(Optional.ofNullable(text).orElse("")).matches();
    }

    public Boolean hasNumbers(@Nullable String text) {
        return Pattern.compile("^(?=.*\\d).*$")
                .matcher(Optional.ofNullable(text).orElse("")).matches();
    }

    public Boolean hasUppercase(@Nullable String text) {
        return Pattern.compile("^(?=.*[A-Z]).*$")
                .matcher(Optional.ofNullable(text).orElse("")).matches();
    }

    public Boolean hasLowercase(@Nullable String text) {
        return Pattern.compile("^(?=.*[a-z]).*$")
                .matcher(Optional.ofNullable(text).orElse("")).matches();
    }

    public Boolean isBetween(@Nullable String text, @NonNull Integer min, @NonNull Integer max) {
        final var range = "{%d,%d}".formatted(min, max);
        return Pattern.compile("^(?=.*)." + range + "$")
                .matcher(Optional.ofNullable(text).orElse("")).matches();
    }

    public Boolean equals(@Nullable String a, @Nullable String b) {
        return Objects.nonNull(a) && a.equals(b);
    }

}
