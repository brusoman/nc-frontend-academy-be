package nc.backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @Getter
    @Setter
    private Long user_id;

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
    private Boolean admin;

    public UserDto(Long user_id, String login, String email, String name, String surname, Boolean admin) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
    }
}
