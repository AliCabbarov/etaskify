package etaskify.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrganizationResponse {
    private long id;
    private String name;
    private String phone;
    private String address;
}
