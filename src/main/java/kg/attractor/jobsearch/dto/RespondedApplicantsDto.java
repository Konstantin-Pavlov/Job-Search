package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicantsDto {
    private Integer resumeId;
    private Integer vacancyId;
    private boolean confirmation;
}
