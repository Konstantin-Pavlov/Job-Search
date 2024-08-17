package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.mapper.CustomVacancyMapper;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyDao.getVacancies();
        List<VacancyDto> dtos = new ArrayList<>();
        vacancies.forEach(e -> dtos.add(VacancyDto.builder()
                .id(e.getId())
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
        Vacancy vacancy = vacancyDao.getVacancyById(id)
                .orElseThrow(() -> {
                    log.error("Can't find Vacancy with id {}", id);
                    return new VacancyNotFoundException("Can't find Vacancy with id " + id);
                });
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

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = CustomVacancyMapper.fromDto(vacancyDto);
        vacancyDao.addVacancy(vacancy);
        log.info("added vacancy {}", vacancy.getName());
    }

    @Override
    public boolean deleteVacancy(Integer id) {
        Optional<Vacancy> vacancy = vacancyDao.getVacancyById(id);
        if (vacancy.isPresent()) {
            vacancyDao.delete(id);
            log.info("vacancy deleted: {}", vacancy.get().getName());
            return true;
        }
        log.info(String.format(" vacancy with id %d not found", id));
        return false;
    }

    @Override
    public List<VacancyDto> getVacanciesUserResponded(Integer userId) {
        List<Vacancy> vacancies = vacancyDao.getVacanciesUserResponded(userId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(CustomVacancyMapper::toDto)
                .toList();
        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with user id {}", userId);
        } else {
            log.info("found vacancies with user id {}", userId);
        }
        return dtos;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryId(Integer categoryId) {
        List<Vacancy> vacancies = vacancyDao.getVacanciesByCategoryId(categoryId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(CustomVacancyMapper::toDto)
                .toList();
        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with category id {}", categoryId);
        } else {
            log.info("found vacancies with category id {}", categoryId);
        }
        return dtos;
    }

    @Override
    public void editVacancy(Long id, VacancyDto vacancyDto) {
        // todo - implement vacancy editing
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        // todo - implement
        return List.of();
    }

    @Override
    public List<VacancyDto> getVacancyByAuthorId(Integer id) {
        List<Vacancy> vacancies = vacancyDao.getVacanciesByAuthorId(id);
        return vacancies.stream()
                .map(CustomVacancyMapper::toDto)
                .toList();
    }

}

