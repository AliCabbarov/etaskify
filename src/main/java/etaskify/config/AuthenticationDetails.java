package etaskify.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
@AllArgsConstructor
@Getter
public class AuthenticationDetails {
    private WebAuthenticationDetails webAuthenticationDetails;
    private long userId;
    private String username;
    private long organizationId;
}
