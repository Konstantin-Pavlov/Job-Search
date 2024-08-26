package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.mapper.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    private final ResumeRepository resumeRepository;

    private final ResumeMapper resumeMapper = ResumeMapper.INSTANCE;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = resumeDao.getResumes();
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
                .id(e.getId())
                .applicantId(e.getApplicantId())
                .name(e.getName())
                .categoryId(e.getCategoryId())
                .salary(e.getSalary())
                .isActive(e.getIsActive())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()
        ));
        return dtos;
    }

    @Override
    public ResumeDto getResumeById(Integer id) {
        try {
            Resume resume = resumeDao.getResumeById(id)
                    .orElseThrow(() -> new Exception("Can't find resume with id " + id));
            log.info("found resume with id {}", id);
            return ResumeDto.builder()
                    .id(resume.getId())
                    .applicantId(resume.getApplicantId())
                    .name(resume.getName())
                    .categoryId(resume.getCategoryId())
                    .salary(resume.getSalary())
                    .isActive(resume.getIsActive())
                    .createdDate(resume.getCreatedDate())
                    .updateTime(resume.getUpdateTime())
                    .build();
        } catch (Exception e) {
            log.error("Can't find resume with id {}", id);
        }
        return null;
    }

    @Override
    public List<ResumeDto> getResumesByCategoryId(Integer categoryId) {
        return resumeRepository.findByCategoryId(categoryId).stream()
                .map(resumeMapper::toResumeDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumeByUserId(Integer userId) {
        List<Resume> resumes = resumeDao.getResumesByUserId(userId);
        List<ResumeDto> dtos = resumes.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            log.error("Can't find resumes with user id {}", userId);
        } else {
            log.info("found resumes with user id {}", userId);
        }
        return dtos;
    }

    @Override
    public List<ResumeDto> getResumeByCategory(String category) {
        return resumeDao.getResumeByCategory(category);
    }

    @Override
    public List<ResumeDto> getResumesRespondedToEmployerVacancies(Integer userId) {
        return resumeRepository.findResumesRespondedToEmployerVacancies(userId)
                .stream()
                .map(resumeMapper::toResumeDto)
                .toList();
    }

    @Override
    public void editResume(Integer id, ResumeDto resumeDto) {
        ResumeDto resumeDto1 = getResumeById(id);
        if (resumeDto1 == null) {
            log.error("Can't edit resume because resume with id {} not found", id);
        } else {
            deleteResume(id);
            addResume(resumeDto);
        }
    }

    @Override
    public void addResume(ResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(resumeDto.getCreatedDate());
        resume.setUpdateTime(resumeDto.getUpdateTime());

        resumeDao.addResume(resume);
        log.info("added resume {}", resume.getName());
    }


    @Override
    public boolean deleteResume(Integer id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isPresent()) {
            resumeDao.delete(id);
            log.info("resume deleted: {}", resume.get().getName());
            return true;
        }
        log.info(String.format(" resume with id %d not found", id));
        return false;
    }

    @Override
    public void editResume(ResumeDto resumeDto) {
        ResumeDto existingResume = getResumeById(resumeDto.getId());
        resumeDto.setCreatedDate(existingResume.getCreatedDate());
        resumeDto.setUpdateTime(LocalDateTime.now());
        Resume resume = resumeMapper.toResume(resumeDto);
        resumeRepository.save(resume);
        log.info("edited resume {}", resume.getName());
    }

    @Override
    public void updateResume(Integer resumeId) {
        ResumeDto existingResume = getResumeById(resumeId);
        existingResume.setUpdateTime(LocalDateTime.now());
        Resume resume = resumeMapper.toResume(existingResume);
        resumeRepository.save(resume);
        log.info("updated resume {}", resume.getName());
    }

}
