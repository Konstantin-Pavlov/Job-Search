package kg.attractor.jobsearch.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "User dto")
public class UserDto {
    Integer id;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z]+(?: [a-zA-Z]+)?$", message = "should only contain letters")
    String name;
    @Min(value = 18, message = "Age must be at least {value}")
    @Max(value = 70, message = "Age must be at most {value}")
    Integer age;
    @NotBlank
    @Email
    String email;


//    @Pattern(regexp = "^(?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[a-zA-Z]).+$", message = "Should contain at least one uppercase letter, one number")
//    @NotBlank(message = "Password must not be blank")
//    @Size(min = 4, max = 60, message = "Password length must be from {min} to {max} characters")
//
    String password;
    @Pattern(regexp = "^\\+996\\d{7}$", message = "phone number is invalid")
    String phoneNumber;
    String avatar;
    @Pattern(regexp = "^(employer|applicant)$", message = "should only contain \"employer\" or \"applicant\"")
    String accountType;
    boolean enabled;
}
