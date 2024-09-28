package etaskify.service;

import etaskify.model.dto.request.OrganizationRequest;
import etaskify.model.dto.request.TaskRequest;
import etaskify.model.dto.response.MessageResponse;
import etaskify.model.dto.response.TaskResponse;
import etaskify.model.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrganizationService {
    ResponseEntity<MessageResponse> create(OrganizationRequest request);

    ResponseEntity<MessageResponse> createTask(TaskRequest request);

    ResponseEntity<Page<TaskResponse>> getAllAvailableTask(int pageNumber,int pageSize);
    Organization getById(long id);

}
