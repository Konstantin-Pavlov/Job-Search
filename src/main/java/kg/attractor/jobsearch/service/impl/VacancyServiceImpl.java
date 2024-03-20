package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyDao.getVacancy();
        List<VacancyDto> dtos = new ArrayList<>();
        vacancies.forEach(e -> dtos.add(VacancyDto.builder()
                .name(e.getName())
                .description(e.getDescription())
                .categoryId(e.getCategoryId())
                .salary(e.getSalary())
                .expFrom(e.getExpFrom())
                .expTo(e.getExpTo())
                .isActive(e.getIsActive())
                .authorId(e.getAuthorId())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    @Override
    public VacancyDto getVacancyById(long id) {
            try {
                Vacancy vacancy = vacancyDao.getVacancyById(id)
                        .orElseThrow(() -> new Exception("Can't find Vacancy with id " + id));
                return VacancyDto.builder()
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
            } catch (Exception e) {
                log.error("Can't find Vacancy with id " + id);
            }
            return null;
    }
}
