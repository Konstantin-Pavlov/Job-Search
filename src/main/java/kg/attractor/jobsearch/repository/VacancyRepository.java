package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    @Query("SELECT DISTINCT v FROM Vacancy v " +
            "JOIN RespondedApplicant ra ON v.id = ra.vacancyId " +
            "JOIN Resume r ON ra.resumeId = r.id " +
            "JOIN User u ON r.applicantId = u.id " +
            "WHERE u.id = :userId")
    List<Vacancy> findVacanciesUserResponded(@Param("userId") Integer userId);

    List<Vacancy> findVacanciesByCategoryId(@Param("categoryId") Integer categoryId);

    List<Vacancy> findVacanciesByAuthorId(@Param("authorId") Integer authorId);


}
