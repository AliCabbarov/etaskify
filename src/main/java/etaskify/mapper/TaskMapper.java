package etaskify.mapper;

import etaskify.model.dto.request.TaskRequest;
import etaskify.model.dto.response.TaskResponse;
import etaskify.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "users",ignore = true)
    Task map(TaskRequest request, @MappingTarget Task task);
    TaskResponse map(Task task);
}
