package etaskify.controller;

import etaskify.model.dto.request.PasswordRequest;
import etaskify.model.dto.request.UserRequest;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.UserResponse;
import etaskify.model.entity.User;
import etaskify.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid UserRequest request){
        return userService.create(request);
    }
    @PatchMapping
    public ResponseEntity<MessageResponse> confirm(@RequestParam String token,
                                                   @RequestBody @Valid PasswordRequest request){
        return userService.confirm(request,token);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllOrgUser(){
        return userService.getAllOrganizationUser();
    }
}
