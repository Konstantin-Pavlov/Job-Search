package kg.attractor.jobsearch.dao;


import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vacancy> getVacancies() {
        String sql = """
                select * from VACANCIES
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public Optional<Vacancy> getVacancyById(long id) {
        String sql = """
                select * from VACANCIES
                where ID = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id)
        ));
    }

    public void addVacancy(Vacancy vacancy) {
        String sql = """
                insert into VACANCIES(NAME, DESCRIPTION, CATEGORY_ID, SALARY, EXP_FROM, EXP_TO, IS_ACTIVE, AUTHOR_ID, CREATED_DATE, UPDATE_TIME)
                values (:name, :description, :categoryId, :salary, :expFrom, :expTo, :isActive, :authorId, :createdDate, :updateTime);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("categoryId", vacancy.getCategoryId())
                .addValue("salary", vacancy.getSalary())
                .addValue("expFrom", vacancy.getExpFrom())
                .addValue("expTo", vacancy.getExpTo())
                .addValue("isActive", vacancy.getIsActive())
                .addValue("authorId", vacancy.getAuthorId())
                .addValue("createdDate", vacancy.getCreatedDate())
                .addValue("updateTime", vacancy.getUpdateTime()));
    }

    public void delete(Integer id) {
        String sql = """
                delete from VACANCIES
                where ID=?
                """;
        template.update(sql, id);
    }

    public List<Vacancy> getVacanciesUserResponded(Integer userId) {
        String sql = """
                                SELECT   v.id,
                                         v.NAME,
                //                         v.NAME AS position,
                //                         u.name AS applicant_name,
                                         v.description,
                                         v.category_id,
                                         v.salary,
                                         v.EXP_FROM,
                                         v.EXP_TO,
                                         v.is_active,
                                         v.author_id,
                                         v.created_date,
                                         v.update_time
                                FROM responded_applicants ra
                                           JOIN vacancies v ON ra.VACANCY_ID = v.id
                                           JOIN resumes r ON ra.RESUME_ID = r.id
                                           JOIN users u ON r.applicant_id = u.id
                                WHERE u.id = ?
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }

    public List<Vacancy> getVacanciesByCategoryId(Integer categoryId) {
        String sql = """
                SELECT * FROM VACANCIES
                WHERE CATEGORY_ID = ?;
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
    }

    public void applyForVacancy(Integer resumeId, Integer vacancyId) {
        String sql = """
                INSERT INTO responded_applicants (RESUME_ID, VACANCY_ID, CONFIRMATION)
                VALUES(:resumeId, :vacancyId, :confirmation);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("resumeId", resumeId)
                .addValue("vacancyId", vacancyId)
                .addValue("confirmation", true)
        );
    }
}
