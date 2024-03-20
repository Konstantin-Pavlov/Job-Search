package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}
