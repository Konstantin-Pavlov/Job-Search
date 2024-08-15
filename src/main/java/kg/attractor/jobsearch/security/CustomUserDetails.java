package kg.attractor.jobsearch.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter

public class CustomUserDetails implements UserDetails {
    Integer id;
    String username; // This should be the email
    Integer age;
    String email;
    String password;
    String phoneNumber;
    String avatar;
    String accountType;
    boolean enabled;
    Collection<? extends GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return email; // Ensure this returns the email
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
