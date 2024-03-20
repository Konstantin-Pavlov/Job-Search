package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    public List<Resume> getResume(){
        String sql = """
                select * from resumes
                """;

        return template.query(sql,new BeanPropertyRowMapper<>(Resume.class));
    }

    public Optional<Resume> getResumeById(long id) {
        String sql = """
                select * from resumes
                where ID = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
        ));
    }
}