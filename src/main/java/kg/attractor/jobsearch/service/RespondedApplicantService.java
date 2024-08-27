package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface RespondedApplicantService {
    List<RespondedApplicantDto> getRespondedApplicants();
    boolean checkIfUserRespondedToVacancy(List<ResumeDto> userResumes, Integer vacancyId);
}
