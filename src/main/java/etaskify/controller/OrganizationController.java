package etaskify.controller;

import etaskify.model.dto.request.OrganizationRequest;
import etaskify.model.dto.request.TaskRequest;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.TaskResponse;
import etaskify.service.OrganizationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organization")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrganizationRequest request) {
        return organizationService.create(request);
    }

    @PostMapping("/task")
    public ResponseEntity<MessageResponse> createTask(@RequestBody @Valid TaskRequest request){
        return organizationService.createTask(request);
    }

    @GetMapping("/task")
    public ResponseEntity<Page<TaskResponse>> getAllAvailableTask(@RequestParam(required = false,defaultValue = "0") int pageNumber,
                                                                  @RequestParam(required = false,defaultValue = "10") int pageSize){
        return organizationService.getAllAvailableTask(pageNumber,pageSize);
    }

}
