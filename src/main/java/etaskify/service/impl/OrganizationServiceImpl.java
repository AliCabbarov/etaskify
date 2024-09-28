package etaskify.service.impl;

import etaskify.config.AuthenticationDetails;
import etaskify.mapper.OrganizationMapper;
import etaskify.mapper.TaskMapper;
import etaskify.mapper.UserMapper;
import etaskify.model.dto.request.OrganizationRequest;
import etaskify.model.dto.request.TaskRequest;
import etaskify.model.dto.response.ExceptionResponse;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.TaskResponse;
import etaskify.model.entity.Organization;
import etaskify.model.entity.Role;
import etaskify.model.entity.Task;
import etaskify.model.entity.User;
import etaskify.model.exception.NotFoundException;
import etaskify.repository.OrganizationRepository;
import etaskify.repository.RoleRepository;
import etaskify.repository.TaskRepository;
import etaskify.repository.UserRepository;
import etaskify.service.EmailService;
import etaskify.service.OrganizationService;
import etaskify.service.TokenService;
import etaskify.service.UserService;
import etaskify.util.EmailUtil;
import etaskify.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static etaskify.model.enums.Exceptions.NOT_FOUND;
import static etaskify.model.enums.Messages.*;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final MessageUtil messageUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailUtil emailUtil;
    private final EmailService emailService;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> create(OrganizationRequest request) {
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow(()-> NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(),NOT_FOUND.getStatus()),"ROLE_ADMIN"));

        Organization organization = organizationMapper.map(request, new Organization());
        Organization savedOrganization = organizationRepository.save(organization);

        User user = userMapper.map(request.getUser(), new User(savedOrganization,roleAdmin));

        userRepository.save(user);

        String confirmationToken = tokenService.generateAndSaveConfirmationToken(user);

        String subject = emailUtil.confirmEmailSubjectBuilder(confirmationToken, user.getUsername());

        emailService.sendTo(user.getEmail(),subject);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SEND_TO_EMAIL.getMessage()), MESSAGE_SEND_TO_EMAIL.getStatus()));
    }

    @Override
    public ResponseEntity<MessageResponse> createTask(TaskRequest request) {
        List<User> users = userService.findAllById(request.getUsers());
        Organization organization = getById(userService.getOrgId());
        Task task = taskMapper.map(request, new Task(users,organization));

        String subject = emailUtil.taskMessageSubjectBuilder(task);
        taskRepository.save(task);

        users.forEach(user -> emailService.sendTo(user.getEmail(),subject));

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<Page<TaskResponse>> getAllAvailableTask(int pageNumber,int pageSize) {
        Page<Task> tasks = taskRepository.findAllByOrganization(getById(userService.getOrgId()), PageRequest.of(pageNumber ,pageSize));

        Page<TaskResponse> taskResponses = tasks.map(taskMapper::map);

        return ResponseEntity.ok(taskResponses);
    }

    public Organization getById(long id){
        return organizationRepository.findById(id).orElseThrow(()->NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()),id));
    }

}
