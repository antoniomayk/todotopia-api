package dev.antoniomayk.todotopia.api.common.exception;

import dev.antoniomayk.todotopia.api.common.constant.UserMessageCode;
import dev.antoniomayk.todotopia.api.core.exception.ServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPasswordLengthException extends ServerErrorException {

    private final Integer min;

    private final Integer max;

    @Override
    public String getMessageCode() {
        return UserMessageCode.PASSWORD_LENGTH;
    }

}
