package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

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
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "vacancies").permitAll()
                                .requestMatchers(HttpMethod.GET, "/applicant/vacancies").permitAll()
                                .requestMatchers(HttpMethod.GET, "/applicant/get-user-vacancies/{user_id}").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/applicant/avatar/{userId}").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ADMIN")
//                        .requestMatchers("/quizzes/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.POST, "/applicant/add").permitAll()
                                .requestMatchers(HttpMethod.POST, "/applicant/add-with-avatar").permitAll()
                                .requestMatchers(HttpMethod.POST, "/applicant/{userId}/upload-avatar").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/applicant/resume").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/applicant/vacancy/{vacancyId}/apply").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/applicant/resume/{id}").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/applicant/resume/{id}").hasAnyAuthority("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/employer/resumes").permitAll()
                                .requestMatchers(HttpMethod.GET, "/employer/resumes/category/{category}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/employer/vacancy/{vacancyId}/applicants").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/employer/add").permitAll()
                                .requestMatchers(HttpMethod.POST, "/employer/vacancy").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/employer/vacancy/{id}").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/employer/vacancy/{id}").hasAnyAuthority("ADMIN", "USER")

                                .anyRequest().authenticated()
                );
        return http.build();
    }
}
