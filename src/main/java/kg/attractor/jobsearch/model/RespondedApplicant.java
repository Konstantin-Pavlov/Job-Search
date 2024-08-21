package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@Table(name = "RESPONDED_APPLICANTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class RespondedApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer resumeId;
    private Integer vacancyId;
    private boolean confirmation;
}
