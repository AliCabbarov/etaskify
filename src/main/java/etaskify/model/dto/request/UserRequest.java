package etaskify.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import static etaskify.model.contant.ValidationExceptions.*;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String name;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String surname;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String username;
    @Email(message = EMAIL_EXCEPTION)
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String email;
}

