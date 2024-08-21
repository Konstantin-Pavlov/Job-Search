package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //    private final PasswordEncoder encoder;
    private final DataSource dataSource;

    private static final String USER_QUERY =
            """
                            SELECT EMAIL, PASSWORD, ENABLED
                            FROM USERS
                            WHERE EMAIL=?
                    """;
    private static final String AUTHORITIES_QUERY =
            """
                    select USERS.EMAIL, A.ROLE
                    from USERS
                             inner join ROLES UA on USERS.ID = UA.USER_ID
                             inner join AUTHORITIES A on A.ID = UA.AUTHORITY_ID
                    where EMAIL=?;
                    """;

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .authorities("FULL")
//                .build();
//
//        UserDetails guest = User.builder()
//                .username("guest")
//                .password(encoder.encode("123"))
//                .roles("GUEST")
//                .authorities("READ_ONLY")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, guest);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**").permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/users",
                                "/applicant/vacancies",
                                "/employer/resumes",
                                "/employer/resumes/category/{category}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/applicant/add",
                                "/applicant/add-with-avatar",
                                "/employer/add")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/applicant/get-user-vacancies/{user_id}",
                                "/applicant/avatar/{userId}",
                                "/users/{id}",
                                "/employer/vacancy/{vacancyId}/applicants")
                        .hasAnyAuthority("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/applicant/{userId}/upload-avatar",
                                "/applicant/resume",
                                "/applicant/vacancy/{vacancyId}/apply",
                                "/employer/vacancy")
                        .hasAnyAuthority("ADMIN", "USER")

                        .requestMatchers(HttpMethod.PUT,
                                "/applicant/resume/{id}",
                                "/employer/vacancy/{id}")
                        .hasAnyAuthority("ADMIN", "USER")

                        .requestMatchers(HttpMethod.DELETE,
                                "/applicant/resume/{id}",
                                "/employer/vacancy/{id}")
                        .hasAnyAuthority("ADMIN", "USER")

                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
