package etaskify.service;

import etaskify.model.dto.request.PasswordRequest;
import etaskify.model.dto.request.UserRequest;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.UserResponse;
import etaskify.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<MessageResponse> create(UserRequest request);

    ResponseEntity<MessageResponse> confirm(PasswordRequest request, String token);
    List<User> findAllById(List<Long> ids);
    long getOrgId();

    ResponseEntity<List<UserResponse>> getAllOrganizationUser();
}
