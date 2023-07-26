package me.janek.dairy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class DairyException extends RuntimeException {

    private final Map<String, String> validation = new ConcurrentHashMap<>();

    public DairyException(String message) {
        super(message);
    }

    public DairyException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

}
