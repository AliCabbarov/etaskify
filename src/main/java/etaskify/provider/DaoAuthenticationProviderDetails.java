package etaskify.provider;

import etaskify.config.AuthenticationDetails;
import etaskify.model.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class DaoAuthenticationProviderDetails extends DaoAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken successAuthentication =
                (UsernamePasswordAuthenticationToken) super.createSuccessAuthentication(principal, authentication, userDetails);
        User user = (User) userDetails;
        successAuthentication.setDetails(new AuthenticationDetails(null,
                user.getId(),
                user.getEmail(),
                user.getOrganization().getId()));
        return successAuthentication;
    }
}
