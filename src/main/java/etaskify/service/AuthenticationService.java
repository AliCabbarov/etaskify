package etaskify.service;

import etaskify.model.dto.request.LoginRequest;
import etaskify.model.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface AuthenticationService {
    ResponseEntity<TokenResponse> login(LoginRequest request);
    ResponseEntity<TokenResponse> tokenByRefreshToken(String token);
}
