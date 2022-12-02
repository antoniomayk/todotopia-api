package dev.antoniomayk.todotopia.api.common.exception;

import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;
import dev.antoniomayk.todotopia.api.core.exception.ServerErrorException;

public class UserPasswordSymbolsException extends ServerErrorException {

    @Override
    public String getMessageCode() {
        return UserMessageCode.PASSWORD_SYMBOLS;
    }

}
