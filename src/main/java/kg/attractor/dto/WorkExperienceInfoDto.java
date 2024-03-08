package kg.attractor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfoDto {
    private Long applicantId;
    private String name;
    private Long categoryId;
    private Integer salary;
    private Boolean isActive;
    private Date createdDate;
    private Date updateTime;
}
