package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();

    VacancyDto getVacancyById(Integer id);

    void createVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Integer id);

    List<VacancyDto> getVacanciesUserResponded(Integer userId);

    List<VacancyDto> getVacanciesByCategoryId(Integer categoryId);

    void editVacancy(VacancyDto vacancyDto);

    List<VacancyDto> getVacanciesByCategory(String category);

    List<VacancyDto> getVacancyByAuthorId(Integer id);

    void updateVacancy(Integer vacancyId);

    List<ResumeDto> findResumesRespondedToEmployerVacancies(Integer id);
}
