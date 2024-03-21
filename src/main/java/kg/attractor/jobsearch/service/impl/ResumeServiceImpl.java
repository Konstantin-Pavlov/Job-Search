package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = resumeDao.getResume();
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
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
    public ResumeDto getResumeById(long id) {
        try {
            Resume resume = resumeDao.getResumeById(id)
                    .orElseThrow(() -> new Exception("Can't find resume with id " + id));
            log.info("found resume with id " + id);
            return ResumeDto.builder()
                    .applicantId(resume.getApplicantId())
                    .name(resume.getName())
                    .categoryId(resume.getCategoryId())
                    .salary(resume.getSalary())
                    .isActive(resume.getIsActive())
                    .createdDate(resume.getCreatedDate())
                    .updateTime(resume.getUpdateTime())
                    .build();
        } catch (Exception e) {
            log.error("Can't find resume with id " + id);
        }
        return null;
    }

    @Override
    public ResumeDto getResumeByCategoryId(Integer categoryId) {
        try {
            Resume resume = resumeDao.getResumeByCategoryId(categoryId)
                    .orElseThrow(() -> new Exception("Can't find resume with categoryId " + categoryId));
            log.info("found resume with categoryId " + categoryId);
            return ResumeDto.builder()
                    .applicantId(resume.getApplicantId())
                    .name(resume.getName())
                    .categoryId(resume.getCategoryId())

                    .salary(resume.getSalary())
                    .isActive(resume.getIsActive())
                    .createdDate(resume.getCreatedDate())
                    .updateTime(resume.getUpdateTime())
                    .build();
        } catch (Exception e) {
            log.error("Can't find resume with categoryId" + categoryId);
        }
        return null;
    }

    @Override
    public List<ResumeDto> getResumeByUserId(Integer userId) {
        List<Resume> resumes = resumeDao.getResumesByUserId(userId);
        List<ResumeDto> dtos = resumes.stream()
                .map(e -> ResumeDto.builder()
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
            log.error("Can't find resumes with user id " + userId);
        } else {
            log.info("found resumes with user id " + userId);
        }
        return dtos;
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
        log.info("added resume " + resume.getName());
    }


    @Override
    public boolean deleteResume(Integer id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isPresent()) {
            resumeDao.delete(id);
            log.info("resume deleted: " + resume.get().getName());
            return true;
        }
        log.info(String.format(" resume with id %d not found", id));
        return false;
    }

}
