package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface VacancyMapper {
    VacancyMapper INSTANCE = Mappers.getMapper(VacancyMapper.class);

    VacancyDto toVacancyDto(Vacancy vacancy);

    Vacancy toVacancy(VacancyDto vacancyDto);
    List<VacancyDto> toVacancyDto(List<Vacancy> vacancies);
}
