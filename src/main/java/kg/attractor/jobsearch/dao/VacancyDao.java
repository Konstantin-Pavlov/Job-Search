package kg.attractor.jobsearch.dao;


import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;

    public List<Vacancy> getVacancy() {
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


}
