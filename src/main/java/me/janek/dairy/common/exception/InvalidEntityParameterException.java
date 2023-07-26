package me.janek.dairy.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidEntityParameterException extends DairyException {

    private static final String DEFAULT_MESSAGE = "잘못된 파라미터 값입니다.";

    public InvalidEntityParameterException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidEntityParameterException(String message) {
        super(message);
    }

    public InvalidEntityParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
