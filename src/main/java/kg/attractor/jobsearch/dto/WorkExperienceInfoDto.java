package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("applicant_id")
    Integer applicantId;

    String name;

    @JsonProperty("category_id")
    Integer categoryId;

    Integer salary;

    @JsonProperty("is_active")
    Boolean isActive;

    @JsonProperty("created_date")
    LocalDateTime createdDate;

    @JsonProperty("update_time")
    LocalDateTime updateTime;
}
