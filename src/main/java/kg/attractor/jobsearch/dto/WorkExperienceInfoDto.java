package kg.attractor.jobsearch.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkExperienceInfoDto {
    Integer applicantId;
    String name;
    Integer categoryId;
    Integer salary;
    Boolean isActive;
    LocalDateTime createdDate;
    LocalDateTime updateTime;
}
