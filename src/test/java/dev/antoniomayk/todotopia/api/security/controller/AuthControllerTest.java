package dev.antoniomayk.todotopia.api.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;
import dev.antoniomayk.todotopia.api.common.dto.UserDTO;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.hateoas.GenericResource;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.reactor.http.client.ReactorHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class AuthControllerTest {

    @Inject
    @Client("/")
    ReactorHttpClient client;

    @Inject
    MessageSource messageSource;

    void assertUserCreated(UserDTO userDTO) throws JsonProcessingException {
        final var body = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build().writeValueAsString(userDTO);
        StepVerifier.create(client.exchange(HttpRequest.POST("sign-up", body)))
                .consumeNextWith(response -> {
                    assertThat(response.getStatus().getCode()).isEqualTo(HttpStatus.CREATED.getCode());
                    assertThat(response.getBody(UserDTO.class)).get()
                            .satisfies(dto -> {
                                assertThat(dto.getFirstName()).isEqualTo(dto.getFirstName());
                                assertThat(dto.getLastName()).isEqualTo(dto.getLastName());
                                assertThat(dto.getEmail()).isEqualTo(dto.getEmail());
                            });
                }).verifyComplete();
    }

    void assertErrorMessage(HttpStatus httpStatus, String message, UserDTO userDTO) throws JsonProcessingException {
        final var body = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build().writeValueAsString(userDTO);
        StepVerifier.create(client.retrieve(HttpRequest.POST("sign-up", body), UserDTO.class))
                .expectErrorSatisfies(throwable -> {
                    final var responseException = (HttpClientResponseException) throwable;
                    final var response = responseException.getResponse();
                    assertThat(response.getStatus().getCode()).isEqualTo(httpStatus.getCode());
                    assertThat(response.getBody(JsonError.class)).get()
                            .extracting(jsonError -> jsonError.getEmbedded().get("errors")).asInstanceOf(InstanceOfAssertFactories.OPTIONAL).get().asInstanceOf(InstanceOfAssertFactories.LIST).singleElement()
                            .extracting(resource -> ((GenericResource) resource).getAdditionalProperties().get("message")).asInstanceOf(InstanceOfAssertFactories.STRING)
                            .isEqualTo(message);
                }).verify();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnCreatedWhenUserHasValidCredentials.csv", numLinesToSkip = 1)
    void shouldReturnCreatedWhenUserHasValidCredentials(String email, String password) throws JsonProcessingException {
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertUserCreated(userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserPasswordHasNoNumbers.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserPasswordHasNoNumbers(String email, String password) throws JsonProcessingException {
        final var message = messageSource.getMessage(UserMessageCode.PASSWORD_NUMBERS, Locale.getDefault()).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserPasswordHasNoSymbols.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserPasswordHasNoSymbols(String email, String password) throws JsonProcessingException {
        final var message = messageSource.getMessage(UserMessageCode.PASSWORD_SYMBOLS, Locale.getDefault()).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserPasswordHasNoLowercase.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserPasswordHasNoLowercase(String email, String password) throws JsonProcessingException {
        final var message = messageSource.getMessage(UserMessageCode.PASSWORD_LOWERCASE, Locale.getDefault()).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserPasswordHasNoUppercase.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserPasswordHasNoUppercase(String email, String password) throws JsonProcessingException {
        final var message = messageSource.getMessage(UserMessageCode.PASSWORD_UPPERCASE, Locale.getDefault()).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserPasswordIsNotBetween.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserPasswordIsNotBetween(String email, String password) throws JsonProcessingException {
        final var variables = new HashMap<String, Object>() {{
            put("min", 8);
            put("max", 64);
        }};
        final var message = messageSource.getMessage(UserMessageCode.PASSWORD_LENGTH, Locale.getDefault(), variables).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserEmailAddressIsInvalid.csv", numLinesToSkip = 1, delimiter = '_')
    void shouldReturnBadRequestWhenUserEmailAddressIsInvalid(String email, String password) throws JsonProcessingException {
        final var message = messageSource.getMessage(UserMessageCode.EMAIL_INVALID, Locale.getDefault()).orElseThrow();
        final var userDTO = UserDTO.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();
        assertErrorMessage(HttpStatus.BAD_REQUEST, message, userDTO);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldReturnBadRequestWhenUserEmailAddressIsAlreadyInUse.csv", numLinesToSkip = 1)
    void shouldReturnBadRequestWhenUserEmailAddressIsAlreadyInUse(String email1, String password1, String email2, String password2) throws JsonProcessingException {
        final var userDTO1 = UserDTO.builder()
                .email(email1)
                .password(password1)
                .passwordConfirmation(password1)
                .build();
        assertUserCreated(userDTO1);
        final var message = messageSource.getMessage(UserMessageCode.EMAIL_REGISTERED, Locale.getDefault()).orElseThrow();
        final var userDTO2 = UserDTO.builder()
                .email(email2)
                .password(password2)
                .passwordConfirmation(password2)
                .build();
        assertErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, message, userDTO2);
    }

}
