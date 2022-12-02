package dev.antoniomayk.todotopia.api.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMessageCode {

    public final String EMAIL_INVALID = "dev.antoniomayk.todotopia.api.common.exception.UserEmailInvalidException.message";

    public final String EMAIL_REGISTERED = "dev.antoniomayk.todotopia.api.common.exception.UserEmailRegisteredException.message";

    public final String PASSWORD_LENGTH = "dev.antoniomayk.todotopia.api.common.exception.UserPasswordLengthException.message";

    public final String PASSWORD_LOWERCASE = "dev.antoniomayk.todotopia.api.common.exception.UserPasswordLowercaseException.message";

    public final String PASSWORD_UPPERCASE = "dev.antoniomayk.todotopia.api.common.exception.UserPasswordUppercaseException.message";

    public final String PASSWORD_NUMBERS = "dev.antoniomayk.todotopia.api.common.exception.UserPasswordNumbersException.message";

    public final String PASSWORD_SYMBOLS = "dev.antoniomayk.todotopia.api.common.exception.UserPasswordSymbolsException.message";

}
