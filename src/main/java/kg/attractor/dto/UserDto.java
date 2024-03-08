package kg.attractor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {private String name;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}
