package etaskify.model.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static ExceptionResponse of(String message, HttpStatus status) {
        return new ExceptionResponse(message, status);
    }
}
