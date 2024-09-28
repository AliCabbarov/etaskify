package etaskify.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static etaskify.model.contant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private  String username;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private  String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
