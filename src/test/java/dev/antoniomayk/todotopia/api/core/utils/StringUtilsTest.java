package dev.antoniomayk.todotopia.api.core.utils;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static dev.antoniomayk.todotopia.api.core.utils.StringUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class StringUtilsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenTextHasNoNumbers.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenTextHasNoNumbers(String text) {
        assertThat(hasNumbers(text)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenTextHasNumbers.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenTextHasNumbers(String text) {
        assertThat(hasNumbers(text)).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenTextHasNoSymbols.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenTextHasNoSymbols(String text) {
        assertThat(hasSymbols(text)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenTextHasSymbols.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenTextHasSymbols(String text) {
        assertThat(hasSymbols(text)).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenTextHasNoLowercase.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenTextHasNoLowercase(String text) {
        assertThat(hasLowercase(text)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenTextHasLowercase.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenTextHasLowercase(String text) {
        assertThat(hasLowercase(text)).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenTextHasNoUppercase.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenTextHasNoUppercase(String text) {
        assertThat(hasUppercase(text)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenTextHasUppercase.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenTextHasUppercase(String text) {
        assertThat(hasUppercase(text)).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenTextIsNotBetween.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenTextIsNotBetween(String text) {
        assertThat(isBetween(text, 8, 64)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenTextIsBetween.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenTextIsBetween(String text) {
        assertThat(isBetween(text, 8, 64)).isTrue();
    }

}
