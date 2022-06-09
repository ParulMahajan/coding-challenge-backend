package vn.sparkminds.applicationreview.service.mapper;

import org.mapstruct.Mapper;
import vn.sparkminds.applicationreview.entity.Project;
import vn.sparkminds.applicationreview.service.dto.ProjectDto;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDto, Project> {
}
