package etaskify.model.exception;

import etaskify.model.dto.response.ExceptionResponse;
import lombok.Getter;

@Getter
public class UsernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {
    private final ExceptionResponse response;

    public UsernameNotFoundException(ExceptionResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public  static UsernameNotFoundException of(ExceptionResponse exceptionResponse) {
        return new UsernameNotFoundException(exceptionResponse);
    }
}
