package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final String USER_QUERY =
            """
                    SELECT ID, NAME, AGE, EMAIL, PASSWORD, PHONE_NUMBER, AVATAR, ACCOUNT_TYPE, ENABLED
                    FROM USERS
                    WHERE EMAIL=?
                    """;

    private static final String AUTHORITIES_QUERY =
            """
                    SELECT A.ROLE
                    FROM USERS U
                    INNER JOIN ROLES UA ON U.ID = UA.USER_ID
                    INNER JOIN AUTHORITIES A ON A.ID = UA.AUTHORITY_ID
                    WHERE U.EMAIL=?
                    """;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(USER_QUERY, new UserRowMapper(), email);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("User not found with email: " + email, e);
        }
        if (user == null) {
            log.error("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        List<GrantedAuthority> authorities = jdbcTemplate.query(AUTHORITIES_QUERY, new AuthorityRowMapper(), email)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType(),
                user.isEnabled(),
                authorities
        );
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getInt("ID"))
                    .name(rs.getString("NAME"))
                    .age(rs.getInt("AGE"))
                    .email(rs.getString("EMAIL"))
                    .password(rs.getString("PASSWORD"))
                    .phoneNumber(rs.getString("PHONE_NUMBER"))
                    .avatar(rs.getString("AVATAR"))
                    .accountType(rs.getString("ACCOUNT_TYPE"))
                    .enabled(rs.getBoolean("ENABLED"))
                    .build();
        }
    }

    private static class AuthorityRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("ROLE");
        }
    }
}
