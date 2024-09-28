package etaskify.mapper;

import etaskify.model.dto.request.UserRequest;
import etaskify.model.dto.response.UserResponse;
import etaskify.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "organization",ignore = true),
            @Mapping(target = "role",ignore = true),
            @Mapping(target = "enabled",ignore = true),
            @Mapping(target = "password",ignore = true),
    })
    User map(UserRequest request, @MappingTarget User user);
    UserResponse map(User user);
}
