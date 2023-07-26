package me.janek.dairy.common.response;

public record ErrorResponse(String message, String field) {

    public static ErrorResponse cause(String message, String field) {
        return new ErrorResponse(message, field);
    }

}
