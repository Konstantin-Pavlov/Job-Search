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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyDto {
    @NotBlank
    String name;
    @NotBlank
    String description;
    @Min(value = 1, message = "categoryId must be at least 1")
    Integer categoryId;
    @Positive
    Integer salary;
    LocalDateTime expFrom;
    LocalDateTime expTo;
    Boolean isActive;
    @Min(1)
    Integer authorId;
    LocalDateTime createdDate;
    LocalDateTime updateTime;
}
