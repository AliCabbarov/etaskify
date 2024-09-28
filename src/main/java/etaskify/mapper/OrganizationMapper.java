package etaskify.mapper;

import etaskify.model.dto.request.OrganizationRequest;
import etaskify.model.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
    })
    Organization map(OrganizationRequest request, @MappingTarget Organization organization);
}
