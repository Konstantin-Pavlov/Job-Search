package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.mapper.CustomVacancyMapper;
import kg.attractor.jobsearch.mapper.VacancyMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final VacancyRepository vacancyRepository;
    private final CategoryRepository categoryRepository;
    private final VacancyMapper vacancyMapper = VacancyMapper.INSTANCE;

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return vacancies.stream()
                // for some reason doesn't work - it thinks expFrom and expTo are LocalDateTime objects,
                // but they are integers, so we get there errors:
                // java.lang.NoSuchMethodError: 'java.time.LocalDateTime kg.attractor.jobsearch.model.Vacancy.getExpFrom()
//                .map(vacancyMapper::toVacancyDto)
                .map(CustomVacancyMapper::toVacancyDto)
                .toList();
    }

    @Override
    public VacancyDto getVacancyById(Integer id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find Vacancy with id {}", id);
                    return new VacancyNotFoundException("Can't find Vacancy with id " + id);
                });
        // for some reason doesn't work - it thinks expFrom and expTo are LocalDateTime objects,
        // but they are integers, so we get there errors:
        // java.lang.NoSuchMethodError: 'java.time.LocalDateTime kg.attractor.jobsearch.model.Vacancy.getExpFrom()
//        return vacancyMapper.toVacancyDto(vacancy);
        return CustomVacancyMapper.toVacancyDto(vacancy);
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyMapper.toVacancy(vacancyDto);
        vacancyRepository.save(vacancy);
        log.info("added vacancy {}", vacancy.getName());
    }

    @Override
    public boolean deleteVacancy(Integer id) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        if (vacancy.isPresent()) {
            vacancyRepository.delete(vacancy.get());
            log.info("vacancy deleted: {}", vacancy.get().getName());
            return true;
        }
        log.info(String.format(" vacancy with id %d not found", id));
        return false;
    }

    @Override
    public List<VacancyDto> getVacanciesUserResponded(Integer userId) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesUserResponded(userId);
//        List<Vacancy> vacancies = vacancyDao.getVacanciesUserResponded(userId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(CustomVacancyMapper::toVacancyDto)
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
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByCategoryId(categoryId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(vacancyMapper::toVacancyDto)
                .toList();
        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with category id {}", categoryId);
        } else {
            log.info("found vacancies with category id {}", categoryId);
        }
        return dtos;
    }

    @Override
    public void editVacancy(Integer id, VacancyDto vacancyDto) {
        Vacancy existingVacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find Vacancy with id {}", id);
                    return new VacancyNotFoundException("Can't find Vacancy with id " + id);
                });
        CustomVacancyMapper.updateVacancyFromDto(vacancyDto, existingVacancy);
        vacancyRepository.save(existingVacancy);
        log.info("updated vacancy {}", existingVacancy.getName());
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        Optional<Category> categoryOptional = categoryRepository.findByName(category);
        if (categoryOptional.isEmpty()) {
            log.error("Can't find category with name {}", category);
            throw new CategoryNotFoundException(
                    String.format("Can't find category with name %s", category)
            );
        }

        Integer categoryId = categoryOptional.get().getId();
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByCategoryId(categoryId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(CustomVacancyMapper::toVacancyDto)
                .toList();
        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with category name {}", category);
        } else {
            log.info("found vacancies with category name {}", category);
        }
        return dtos;
    }

    @Override
    public List<VacancyDto> getVacancyByAuthorId(Integer id) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByAuthorId(id);
//        List<Vacancy> vacancies = vacancyDao.getVacanciesByAuthorId(id);
        return vacancies.stream()
                // for some reason doesn't work - it thinks expFrom and expTo are LocalDateTime objects,
                // but they are integers, so we get there errors:
                // java.lang.NoSuchMethodError: 'java.time.LocalDateTime kg.attractor.jobsearch.model.Vacancy.getExpFrom()
//                .map(vacancyMapper::toVacancyDto)
                .map(CustomVacancyMapper::toVacancyDto)
                .toList();
    }

}

