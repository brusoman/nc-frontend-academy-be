package nc.backend.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTaskAttemptsDto {
    @Getter
    @Setter
    private List<UserTaskDto> attempts;

    public UserTaskAttemptsDto(List<UserTaskDto> attempts) {
        this.attempts = attempts;
    }
}
