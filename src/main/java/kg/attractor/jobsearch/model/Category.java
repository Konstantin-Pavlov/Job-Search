package kg.attractor.jobsearch.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
    private Integer parentId;
}
