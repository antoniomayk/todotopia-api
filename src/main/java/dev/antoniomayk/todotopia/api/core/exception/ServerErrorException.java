package dev.antoniomayk.todotopia.api.core.exception;

public abstract class ServerErrorException extends RuntimeException {

    public abstract String getMessageCode();

}
