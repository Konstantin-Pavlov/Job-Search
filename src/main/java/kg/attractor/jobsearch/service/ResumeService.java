package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService extends CommonService{
    List<ResumeDto> getResumes();

    ResumeDto getResumeById(long id);

    ResumeDto getResumeByCategoryId(Integer categoryId);

    void addResume(ResumeDto resumeDto);

    boolean deleteResume(Integer id);

    List<ResumeDto> getResumeByUserId(Integer userId);

    List<ResumeDto> getResumeByCategory(String category);

    void editResume(Integer id, ResumeDto resumeDto);
}
