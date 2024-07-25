package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.mapper.ResumeMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ResumeService;
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
public class ResumeServiceImpl implements ResumeService {
    ResumeMapper resumeMapper = ResumeMapper.INSTANCE;
    ResumeRepository resumeRepository;
    CategoryRepository categoryRepository;

    @Override
    public List<ResumeDto> getResumes() {
        return resumeRepository.findAll()
                .stream()
                .map(resumeMapper::toResumeDto)
                .toList();
    }

    @Override
    public ResumeDto getResumeById(Integer id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isPresent()) {
            log.info("Retrieved resume with id: {}", id);
            return resumeMapper.toResumeDto(resume.get());
        }
        logErrorIdMessage(id);
        return null;
    }

    @Override
    public List<ResumeDto> getResumesByCategoryId(Integer categoryId) {
        List<Resume> resumes = resumeRepository.findByCategoryId(categoryId);
        if (resumes.isEmpty()) {
            log.error("Can't find category with id {}", categoryId);
//            throw new RuntimeException("Can't find resume with id " + c
            return null;
        }
        log.info("found resume with id {}", categoryId);
        return resumes.stream().map(resumeMapper::toResumeDto).toList();
    }

    @Override
    public List<ResumeDto> getResumeByUserId(Integer userId) {
        List<Resume> resumes = resumeRepository.findByApplicantId(userId);
        if (resumes.isEmpty()) {
            log.error("Can't find resumes with user id {}", userId);
        } else {
            log.info("found resumes with user id {}", userId);
        }
        return resumes.stream()
                .map(resumeMapper::toResumeDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumeByCategory(String category) {
        // Fetch the category by name
        Optional<Category> optionalCategory = categoryRepository.findByName(category);

        if (optionalCategory.isPresent()) {
            // Get the category ID
            Integer categoryId = optionalCategory.get().getId();

            // Fetch resumes by category ID
            List<Resume> resumes = resumeRepository.findByCategoryId(categoryId);

            log.info("found resumes with category {}", category);

            // Convert to DTOs
            return resumeMapper.toDto(resumes);
        } else {
            log.error("Can't find resumes with category {}", category);
            // If category is not found, return an empty list
            return List.of();
        }
    }

    @Override
    public void editResume(Integer id, ResumeDto resumeDto) {
        if (resumeRepository.findById(id).isPresent()) {
            Resume resume = resumeMapper.toResume(resumeDto);
            resumeRepository.save(resume);
            log.info("edited resume with {}", resume.getName());
        } else {
            logErrorIdMessage(id);
        }
    }

    @Override
    public void addResume(ResumeDto resumeDto) {
        Resume resume = resumeMapper.toResume(resumeDto);
        resumeRepository.save(resume);
        log.info("added resume {}", resume.getName());
    }


    @Override
    public boolean deleteResume(Integer id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isPresent()) {
            resumeRepository.delete(resume.get());
            log.info("resume deleted: {}", resume.get().getName());
            return true;
        }
        logErrorIdMessage(id);
        return false;
    }

    private void logErrorIdMessage(Integer id) {
        log.error("Can't find resume with id {}", id);
    }

}
