package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PasswordEncoder encoder;

//    public List<User> getUser() {
//        String sql = """
//                select * from users
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
//    }

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
                where PHONE_NUMBER = ?;
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


    public Optional<User> getUserById(long id) {
        String sql = """
                select * from users
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                ));
    }

    public void addUser(User user) {
        String sql = """
                insert into users(NAME, AGE, EMAIL, PASSWORD, PHONE_NUMBER, AVATAR, ACCOUNT_TYPE, ENABLED)
                values (:name, :age, :email, :password, :phoneNumber, :avatar, :accountType, :enabled);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("age", user.getAge())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()))
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar())
                .addValue("accountType", user.getAccountType())
                .addValue("enabled", user.isEnabled()));
    }

    public void delete(Long id) {
        String sql = """
                delete from USERS
                where ID=?
                """;
        template.update(sql, id);
    }

    public List<User> getUsersRespondedToVacancy(Integer vacancyId) {
        String sql = """
                 SELECT u.*
                 FROM users u
                          INNER JOIN responded_applicants ra ON u.id = ra.RESUME_ID
                 WHERE ra.VACANCY_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), vacancyId);
    }
}
