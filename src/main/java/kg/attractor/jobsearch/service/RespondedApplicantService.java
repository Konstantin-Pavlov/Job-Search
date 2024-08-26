package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;

import java.util.List;

public interface RespondedApplicantService {
    List<RespondedApplicantDto> getRespondedApplicants();
}
