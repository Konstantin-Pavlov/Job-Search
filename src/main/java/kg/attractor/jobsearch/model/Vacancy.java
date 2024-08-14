package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vacancy {
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private Integer salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
    private Integer authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}

