package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Integer respondedApplicantId;
    private String content;
    private LocalDateTime timestamp;
}
