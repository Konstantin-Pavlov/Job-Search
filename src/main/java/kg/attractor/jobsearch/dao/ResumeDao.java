package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
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
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> getResumes() {
        String sql = """
                select * from resumes
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class));
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

    public Optional<Resume> getResumeByCategoryId(Integer categoryId) {
        String sql = """
                select * from RESUMES
                where CATEGORY_ID = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId)
        ));
    }

    public List<Resume> getResumesByUserId(Integer userId) {
        String sql = """
                select * from RESUMES
                where APPLICANT_ID = ?
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }

    public void addResume(Resume resume) {
        String sql = """
                insert into RESUMES(APPLICANT_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE, CREATED_DATE, UPDATE_TIME)
                values (:applicantId, :name, :categoryId, :salary, :isActive,  :createdDate, :updateTime);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("applicantId", resume.getApplicantId())
                .addValue("name", resume.getName())
                .addValue("categoryId", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("isActive", resume.getIsActive())
                .addValue("createdDate", resume.getCreatedDate())
                .addValue("updateTime", resume.getUpdateTime()));
    }


    public List<ResumeDto> getResumeByCategory(String category) {
        String sql = """
                select * from RESUMES
                inner join CATEGORIES C on C.ID = RESUMES.CATEGORY_ID
                where c.NAME = ?
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(ResumeDto.class), category);
    }

    public void delete(Integer id) {
        String sql = """
                delete from RESUMES
                where ID=?
                """;
        template.update(sql, id);
    }
}
