package kg.attractor.jobsearch.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@Data
@Table(name = "VACANCIES")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@With
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

