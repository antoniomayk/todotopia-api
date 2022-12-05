package dev.antoniomayk.todotopia.api.security.exception;

import dev.antoniomayk.todotopia.api.core.exception.GenericException;
import dev.antoniomayk.todotopia.api.security.constant.SecurityExceptionMessageCode;

public class EmailOrPasswordIncorrect extends GenericException {

    @Override
    public String getMessageCode() {
        return SecurityExceptionMessageCode.EMAIL_OR_PASSWORD_INCORRECT;
    }

}
