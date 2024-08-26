package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.mapper.RespondedApplicantMapper;
import kg.attractor.jobsearch.repository.RespondedApplicantRepository;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final RespondedApplicantMapper respondedApplicantMapper = RespondedApplicantMapper.INSTANCE;

    @Override
    public List<RespondedApplicantDto> getRespondedApplicants() {
        return respondedApplicantRepository.findAll()
                .stream()
                .map(respondedApplicantMapper::toRespondedApplicantDto)
                .toList();
    }
}
