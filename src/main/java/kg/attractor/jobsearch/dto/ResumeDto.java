package kg.attractor.jobsearch.dto;

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
@FieldDefaults(level = AccessLevel.PRIVATE) // all fields are private
public class ResumeDto {
    @Min(1)
    Integer applicantId;
    @NotBlank
    String name;
    @Min(1)
    Integer categoryId;
    @Positive
    double salary;
    Boolean isActive;
    LocalDateTime createdDate;
    LocalDateTime updateTime;
}
