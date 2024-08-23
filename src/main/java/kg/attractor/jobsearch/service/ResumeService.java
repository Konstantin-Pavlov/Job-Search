package kg.attractor.jobsearch.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumes();

    ResumeDto getResumeById(Integer id);

    List<ResumeDto> getResumesByCategoryId(Integer categoryId);

    List<ResumeDto> getResumeByUserId(Integer userId);

    List<ResumeDto> getResumeByCategory(String category);

    void addResume(ResumeDto resumeDto);

    void editResume(Integer id, ResumeDto resumeDto);

    boolean deleteResume(Integer id);

    void updateResume(ResumeDto resumeDto);
}
