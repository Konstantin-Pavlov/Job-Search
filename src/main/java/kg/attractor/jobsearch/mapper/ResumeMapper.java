package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);
    ResumeDto toResumeDto(Resume resume);
    Resume toResume(ResumeDto resumeDto);
    List<ResumeDto> toDto(List<Resume> resumes);
}
