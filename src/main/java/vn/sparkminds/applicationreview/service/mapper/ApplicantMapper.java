package vn.sparkminds.applicationreview.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;

@Mapper(componentModel = "spring")
public interface ApplicantMapper extends EntityMapper<ApplicantDto, Applicant> {

    @Mapping(target = "projects", source = "projects", ignore = true)
    Applicant toEntity(ApplicantDto dto);
}
