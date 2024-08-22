package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Vacancy;

public class CustomVacancyMapper {
    private CustomVacancyMapper() {
    }

    public static VacancyDto toVacancyDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthorId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }

    public static Vacancy toVacancy(VacancyDto vacancyDto) {
        if (vacancyDto == null) return null;
        return Vacancy.builder()
                .id(vacancyDto.getId())
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .categoryId(vacancyDto.getCategoryId())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.getIsActive())
                .authorId(vacancyDto.getAuthorId())
                .createdDate(vacancyDto.getCreatedDate())
                .updateTime(vacancyDto.getUpdateTime())
                .build();
    }

    public static void updateVacancyFromDto(VacancyDto vacancyDto, Vacancy existingVacancy) {
        existingVacancy.setName(vacancyDto.getName());
        existingVacancy.setDescription(vacancyDto.getDescription());
        existingVacancy.setCategoryId(vacancyDto.getCategoryId());
        existingVacancy.setSalary(vacancyDto.getSalary());
        existingVacancy.setExpFrom(vacancyDto.getExpFrom());
        existingVacancy.setExpTo(vacancyDto.getExpTo());
        existingVacancy.setIsActive(vacancyDto.getIsActive());
        existingVacancy.setAuthorId(vacancyDto.getAuthorId());
        existingVacancy.setCreatedDate(vacancyDto.getCreatedDate());
        existingVacancy.setUpdateTime(vacancyDto.getUpdateTime());
    }
}
