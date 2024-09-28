package etaskify.service.impl;

import etaskify.config.AuthenticationDetails;
import etaskify.mapper.UserMapper;
import etaskify.model.dto.request.PasswordRequest;
import etaskify.model.dto.request.UserRequest;
import etaskify.model.dto.response.ExceptionResponse;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.UserResponse;
import etaskify.model.entity.Organization;
import etaskify.model.entity.Role;
import etaskify.model.entity.Token;
import etaskify.model.entity.User;
import etaskify.model.enums.TokenType;
import etaskify.model.exception.NotFoundException;
import etaskify.repository.OrganizationRepository;
import etaskify.repository.RoleRepository;
import etaskify.repository.TokenRepository;
import etaskify.repository.UserRepository;
import etaskify.service.EmailService;
import etaskify.service.OrganizationService;
import etaskify.service.TokenService;
import etaskify.service.UserService;
import etaskify.util.EmailUtil;
import etaskify.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static etaskify.model.enums.Exceptions.NOT_FOUND;
import static etaskify.model.enums.Exceptions.NOT_SAME_PASSWORD;
import static etaskify.model.enums.Messages.MESSAGE_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final MessageUtil messageUtil;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final EmailUtil emailUtil;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> create(UserRequest request) {
        Organization organization = organizationRepository.findById(getOrgId()).orElseThrow(()->
                NotFoundException.of(ExceptionResponse.of(NOT_SAME_PASSWORD.getMessage(), NOT_FOUND.getStatus()),getOrgId()));

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), "ROLE_USER"));

        User user = userMapper.map(request, new User(organization, roleUser));

        userRepository.save(user);

        String confirmationToken = tokenService.generateAndSaveConfirmationToken(user);

        String subject = emailUtil.confirmEmailSubjectBuilder(confirmationToken,user.getUsername());

        emailService.sendTo(user.getEmail(),subject);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> confirm(PasswordRequest request, String token) {
        Token foundedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.CONFIRMATION, token, true).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(
                NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), token));
        foundedToken.isUsableOrElseThrow();

        User user = foundedToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        foundedToken.unusable();
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    public List<User> findAllById(List<Long> ids){
        return userRepository.findAllById(ids);
    }
    public long getOrgId(){
        return ((AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getOrganizationId();
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllOrganizationUser() {
        Organization organization = organizationRepository.findById(getOrgId()).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), getOrgId()));
        List<User> allUsers = userRepository.findByOrganizationAndEnabled(organization, true);
        List<UserResponse> userResponses = allUsers.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok(userResponses);
    }

}
