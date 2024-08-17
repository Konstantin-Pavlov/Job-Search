package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import kg.attractor.jobsearch.util.DateTimeUtil;
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
    Integer id;
    @NotBlank
    String name;

    @NotBlank
    String description;

    @Min(value = 1, message = "categoryId must be at least 1")
    @JsonProperty("category_id")
    Integer categoryId;

    @Positive
    Integer salary;

    @JsonProperty("exp_from")
    Integer expFrom;

    @JsonProperty("exp_to")
    Integer expTo;

    @JsonProperty("is_active")
    Boolean isActive;

    @Min(1)
    @JsonProperty("author_id")
    Integer authorId;

    @JsonProperty("created_date")
    LocalDateTime createdDate;

    @JsonProperty("update_time")
    LocalDateTime updateTime;

    public String getFormattedCreatedDate() {
        return DateTimeUtil.getFormattedDate(createdDate);
    }
    public String getFormattedUpdatedDate() {
        return DateTimeUtil.getFormattedDate(updateTime);
    }
}
