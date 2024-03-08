package kg.attractor.dto;

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
    private Long resumeId;
    private String value;
}
