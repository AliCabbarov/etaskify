package etaskify.filter;

import etaskify.config.AuthenticationDetails;
import etaskify.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        final String jwt;


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


    jwt = authHeader.substring(7);
    userEmail = tokenUtil.extractUsername(jwt);
    List<SimpleGrantedAuthority> grantedAuthorities = tokenUtil.extractAuthorities(jwt);
    int userId = tokenUtil.extractUserId(jwt);
    long orgId = tokenUtil.extractOrganizationId(jwt);



        if (!tokenUtil.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        grantedAuthorities);

        authToken.setDetails(new AuthenticationDetails(new WebAuthenticationDetails(request), userId, userEmail,orgId));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}

