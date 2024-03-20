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
public class WorkExperienceInfoDto {
    private Integer applicantId;
    private String name;
    private Integer categoryId;
    private Integer salary;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
