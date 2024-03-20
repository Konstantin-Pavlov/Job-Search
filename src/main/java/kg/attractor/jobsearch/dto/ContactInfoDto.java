package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class ContactInfoDto {
    private Integer typeId;
    private Integer resumeId;
    private String value;
}
