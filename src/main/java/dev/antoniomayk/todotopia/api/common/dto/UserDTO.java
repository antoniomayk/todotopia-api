package dev.antoniomayk.todotopia.api.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import dev.antoniomayk.todotopia.api.common.annotation.*;
import dev.antoniomayk.todotopia.api.common.constant.UserDTOMessageCode;
import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;
import dev.antoniomayk.todotopia.api.common.entity.User;
import dev.antoniomayk.todotopia.api.core.annotation.EmailRFC5322;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@PasswordAndPasswordConfirmationMustBeEquals(message = UserDTOMessageCode.PASSWORD_AND_PASSWORD_CONFIRMATION_MUST_BE_EQUALS)
public class UserDTO {

    UUID uuid;

    @EmailRFC5322(message = UserMessageCode.EMAIL_INVALID)
    String email;

    @PasswordNumbers(message = UserMessageCode.PASSWORD_NUMBERS)
    @PasswordSymbols(message = UserMessageCode.PASSWORD_SYMBOLS)
    @PasswordUppercase(message = UserMessageCode.PASSWORD_UPPERCASE)
    @PasswordLowercase(message = UserMessageCode.PASSWORD_LOWERCASE)
    @PasswordLength(min = 8, max = 64, message = UserMessageCode.PASSWORD_LENGTH)
    @JsonProperty(access = Access.WRITE_ONLY)
    String password;

    @JsonProperty(access = Access.WRITE_ONLY)
    String passwordConfirmation;

    String firstName;

    String lastName;

    public User toEntity() {
        return new ModelMapper().map(this, User.class);
    }

}
