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
}
