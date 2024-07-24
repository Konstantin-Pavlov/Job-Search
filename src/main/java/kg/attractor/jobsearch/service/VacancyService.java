package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();

    VacancyDto getVacancyById(long id);

    void createVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Integer id);

    List<VacancyDto> getVacanciesUserResponded(Integer userId);

    List<VacancyDto> getVacanciesByCategoryId(Integer categoryId);

    void editVacancy(Long id, VacancyDto vacancyDto);

    List<VacancyDto> getVacanciesByCategory(String category);
}
