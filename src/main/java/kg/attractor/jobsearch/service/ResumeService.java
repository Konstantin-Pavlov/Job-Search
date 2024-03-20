package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumes();

    ResumeDto getResumeById(long id);

    ResumeDto getResumeByCategoryId(Integer categoryId);

    void addResume(ResumeDto resumeDto);

    boolean deleteResume(Integer id);
}
