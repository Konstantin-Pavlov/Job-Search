package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.model.RespondedApplicant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RespondedApplicantMapper {
    RespondedApplicantMapper INSTANCE = Mappers.getMapper(RespondedApplicantMapper.class);

    RespondedApplicant toRespondedApplicant(RespondedApplicantDto respondedApplicantDto);

    RespondedApplicantDto toRespondedApplicantDto(RespondedApplicant respondedApplicant);
}
