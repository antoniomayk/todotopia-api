package dev.antoniomayk.todotopia.api.core.utils;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class EmailUtilsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnFalseWhenEmailAddressIsInvalid.csv", numLinesToSkip = 1)
    void shouldReturnFalseWhenEmailAddressIsInvalid(String email) {
        assertThat(EmailUtils.isValid(email)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnTrueWhenEmailAddressIsValid.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenEmailAddressIsValid(String email) {
        assertThat(EmailUtils.isValid(email)).isTrue();
    }

}
