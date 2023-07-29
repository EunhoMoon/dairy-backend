package me.janek.dairy.common.response;

import lombok.extern.slf4j.Slf4j;
import me.janek.dairy.common.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public List<ErrorResponse> onMethodArgumentNotValid(InvalidRequestException e) {
        var fieldError = e.getErrors();

        return fieldError.stream()
            .map(error -> new ErrorResponse(error.getDefaultMessage(), error.getField()))
            .collect(Collectors.toList());
    }
}
