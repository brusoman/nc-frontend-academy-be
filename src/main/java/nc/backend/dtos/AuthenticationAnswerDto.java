package nc.backend.dtos;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationAnswerDto {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String token;

    public AuthenticationAnswerDto(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
