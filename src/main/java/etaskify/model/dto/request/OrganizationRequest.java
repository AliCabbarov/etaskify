package etaskify.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static etaskify.model.contant.ValidationExceptions.NOT_BLANK_EXCEPTION;
import static etaskify.model.contant.ValidationExceptions.PHONE_PATTERN_EXCEPTION;

@Getter
@AllArgsConstructor
public class OrganizationRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String name;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "[0-9]{10,12}",message = PHONE_PATTERN_EXCEPTION)
    private String phone;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String address;
    @Valid
    private UserRequest user;
}
