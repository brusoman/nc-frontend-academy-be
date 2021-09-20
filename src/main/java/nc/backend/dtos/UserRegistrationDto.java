package nc.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
public class UserRegistrationDto {


    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private boolean admin;


    public UserRegistrationDto(String password, String login, String email, String name, String surname, Boolean admin) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.admin = admin;
    }
}


