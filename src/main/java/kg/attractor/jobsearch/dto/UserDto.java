package kg.attractor.jobsearch.dto;

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
public class UserDto {
    private Integer id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "should only contain letters")
    private String name;
    @Positive
    @Min(18)
    @Max(70)
    private Integer age;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^(?=.\\d)(?=.[a-z])(?=.[A-Z])(?=.[a-zA-Z]).+$", message = "Should contain at least one uppercase letter, one number")
    private String password;
    @Pattern(regexp = "^\\+996\\d{7}$", message = "phone number is invalid")
    private String phoneNumber;
    private String avatar;
    @Pattern(regexp = "^(employer|applicant)$", message = "should only contain \"employer\" or \"applicant\"")
    private String accountType;
}
