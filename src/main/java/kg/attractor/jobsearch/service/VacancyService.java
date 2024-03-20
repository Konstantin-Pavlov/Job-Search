package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();

    VacancyDto getVacancyById(long id);
}
