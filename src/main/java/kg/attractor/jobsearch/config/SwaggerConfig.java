package kg.attractor.jobsearch.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class SwaggerConfig {

    private final UserDetailsService userDetailsService;

    public SwaggerConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public OpenAPI customOpenIpi() {
        // не показывает роль юзера в  свагере
        return new OpenAPI().info(
                new Info()
                        .title("Job Search API")
                        .version("1.0.0")
                        .description("description")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact().name("Your Name").email("your.email@example.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .extensions(Map.of("security", List.of(securityRequirement())))
        );
    }

    private SecurityRequirement securityRequirement() {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            log.warn(username);
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    securityRequirement.addList(authority.getAuthority(), List.of());
                }
            } catch (UsernameNotFoundException e) {
                log.error(e.getMessage());
            }
        }
        return securityRequirement;
    }
}
