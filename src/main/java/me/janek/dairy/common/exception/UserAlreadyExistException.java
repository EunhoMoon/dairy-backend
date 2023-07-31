package me.janek.dairy.common.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends DairyException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public UserAlreadyExistException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }
}
