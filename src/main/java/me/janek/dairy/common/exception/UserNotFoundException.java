package me.janek.dairy.common.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends DairyException {

    private static final String MESSAGE = "요청된 email에 해당하는 회원 정보를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }
}
