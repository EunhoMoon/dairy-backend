package me.janek.dairy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InvalidRequestException extends DairyException {

    private static final String MESSAGE = "요청값이 잘못되었습니다.";

    private final List<FieldError> errors = new ArrayList<>();

    public InvalidRequestException(List<FieldError> errors) {
        super(MESSAGE);
        this.errors.addAll(errors);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
