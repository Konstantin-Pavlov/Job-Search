package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();

    VacancyDto getVacancyById(long id);

    void addVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Integer id);

    List<VacancyDto> getVacanciesUserResponded(Integer userId);
}
