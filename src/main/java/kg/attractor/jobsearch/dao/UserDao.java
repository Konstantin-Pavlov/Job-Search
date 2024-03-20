package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate template;

    public List<User> getUser() {
        String sql = """
                select * from users
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> getUserByName(String name) {
        String sql = """
                select * from users
                where name = ?;
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(User.class), name)
        ));
    }

    public Optional<User> getUserByPhone(String phoneNumber) {
        String sql = """
                select * from users
                where phoneNumber = ?;
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber)
        ));
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = """
                select * from users
                where email = ?;
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(User.class), email)
        ));
    }
}
