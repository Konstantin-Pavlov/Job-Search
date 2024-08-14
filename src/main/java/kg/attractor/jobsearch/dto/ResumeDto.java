package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class ResumeDto {
    @Min(1)
    @JsonProperty("applicant_id")
    Integer applicantId;

    @NotBlank
    String name;

    @Min(value = 1, message = "categoryId must be at least 1")
    @JsonProperty("category_id")
    Integer categoryId;

    @Positive
    double salary;

    @JsonProperty("is_active")
    Boolean isActive;

    @JsonProperty("created_date")
    LocalDateTime createdDate;

    @JsonProperty("update_time")
    LocalDateTime updateTime;
}
