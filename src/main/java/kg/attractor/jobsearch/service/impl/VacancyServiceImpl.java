package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.mapper.VacancyMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VacancyServiceImpl implements VacancyService {
    VacancyRepository vacancyRepository;
    VacancyMapper vacancyMapper = VacancyMapper.INSTANCE;
    CategoryRepository categoryRepository;


    @Override
    public List<VacancyDto> getVacancies() {
        return vacancyRepository
                .findAll()
                .stream().map(vacancyMapper::toVacancyDto)
                .toList();
    }

    @Override
    public VacancyDto getVacancyById(Integer id) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        if (vacancy.isPresent()) {
            log.info("Found vacancy: {}", vacancy.get());
            return vacancyMapper.toVacancyDto(vacancy.get());
        }
        logErrorMessage("Could not find vacancy with id: {}", String.valueOf(id));

        return null;
    }

    @Override
    public List<VacancyDto> getVacanciesUserResponded(Integer userId) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesUserResponded(userId);
        if (vacancies.isEmpty()) {
            logErrorMessage("Could not find vacancies with user id: {}", String.valueOf(userId));
        } else {
            logInfoMessage(vacancies);
        }
        return vacancies.stream().map(vacancyMapper::toVacancyDto).toList();
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryId(Integer categoryId) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByCategoryId(categoryId);
        if (vacancies.isEmpty()) {
            logErrorMessage("Could not find vacancies with category id: {}", String.valueOf(categoryId));
        } else {
            logInfoMessage(vacancies);
        }
        return vacancies.stream().map(vacancyMapper::toVacancyDto).toList();
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        // Fetch the category by name
        Optional<Category> optionalCategory = categoryRepository.findByName(category);
        if (optionalCategory.isPresent()) {
            // Get the category ID
            Integer categoryId = optionalCategory.get().getId();

            // Fetch resumes by category ID
            List<Vacancy> vacancies = vacancyRepository.findVacanciesByCategoryId(categoryId);

            log.info("found vacancies with category {}", category);

            // Convert to DTOs
            return vacancyMapper.toVacancyDto(vacancies);
        } else {
            log.error("Can't find vacancies with category {}", category);
            // If category is not found, return an empty list
            return List.of();
        }
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
        logErrorMessage("Could not find vacancy: {}", String.valueOf(id));

        return false;
    }

    @Override
    public void editVacancy(Integer id, VacancyDto vacancyDto) {
        if (vacancyRepository.findById(id).isPresent()) {
            Vacancy vacancy = vacancyMapper.toVacancy(vacancyDto);
            vacancyRepository.save(vacancy);
            log.info("edited vacancy {}", vacancy.getName());
        } else {
            logErrorMessage("Could not find vacancy: {}", String.valueOf(id));
        }
    }

    private void logErrorMessage(String message, String placeHolder) {
        log.error(message, placeHolder);
    }

    private void logInfoMessage(List<Vacancy> vacancies) {
        log.info("Found vacancies: {}", vacancies);
    }


}

