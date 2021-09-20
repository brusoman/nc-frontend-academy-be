package nc.backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadDto {
    @Getter
    @Setter
    String message;

    public UploadDto(String message) {
        this.message = message;
    }
}
