package etaskify.controller;

import etaskify.model.dto.request.LoginRequest;
import etaskify.model.dto.response.TokenResponse;
import etaskify.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }

    @GetMapping
    public ResponseEntity<TokenResponse> refreshToken(@RequestParam String token){
        return authenticationService.tokenByRefreshToken(token);
    }
}
