package etaskify.service.impl;

import etaskify.config.AuthenticationDetails;
import etaskify.model.dto.request.LoginRequest;
import etaskify.model.dto.response.ExceptionResponse;
import etaskify.model.dto.response.TokenResponse;
import etaskify.model.entity.Token;
import etaskify.model.entity.User;
import etaskify.model.enums.TokenType;
import etaskify.model.exception.NotFoundException;
import etaskify.model.exception.UsernameNotFoundException;
import etaskify.repository.TokenRepository;
import etaskify.repository.UserRepository;
import etaskify.service.AuthenticationService;
import etaskify.service.TokenService;
import etaskify.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static etaskify.model.enums.Exceptions.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();

        Map<String, Object> authorities = getAuthorities(authentication.getAuthorities());
        authorities.put("orgId",details.getOrganizationId());

        String jasonWebToken = tokenUtil.generateToken(details.getUsername(), details.getUserId(), authorities);

        User user = getUserById(details.getUserId());

        String refreshToken = tokenService.generateAndSaveRefreshToken(user);

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<TokenResponse> tokenByRefreshToken(String token) {
        Token findedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.REFRESH, token, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), token));

        findedToken.isUsableOrElseThrow();

        User user = findedToken.getUser();
        String refreshToken = tokenService.generateAndSaveRefreshToken(user);
        Map<String, Object> extraClaims = getAuthorities(user.getAuthorities());

        extraClaims.put("orgId",user.getOrganization().getId());

        String jasonWebToken = tokenUtil.generateToken(user.getEmail(), user.getId(), extraClaims);

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }
    private Map<String, Object> getAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {

        Map<String, Object> extraClaims = new HashMap<>();
        List<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        extraClaims.put("authorities", authorities);
        return extraClaims;
    }
    private User getUserById(long id){
        return  userRepository.findById(id).orElseThrow(() -> UsernameNotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus())));

    }
}
