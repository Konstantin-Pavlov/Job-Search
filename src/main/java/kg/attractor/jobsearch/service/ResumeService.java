package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumes();

    ResumeDto getResumeById(Integer id);

    List<ResumeDto> getResumesByCategoryId(Integer categoryId);

    List<ResumeDto> getResumeByUserId(Integer userId);

    List<ResumeDto> getResumeByCategory(String category);

    List<ResumeDto> getResumesRespondedToEmployerVacancies(Integer userId);

    void addResume(ResumeDto resumeDto);

    void editResume(Integer id, ResumeDto resumeDto);

    boolean deleteResume(Integer id);

    void editResume(ResumeDto resumeDto);

    void updateResume(Integer resumeId);
}
