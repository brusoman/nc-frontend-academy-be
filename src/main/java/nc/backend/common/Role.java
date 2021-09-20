package nc.backend.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Role {
    @Getter
    String name;

    public Role(boolean role) {
        if (role){
            this.name = "ADMIN";
        }

        else{
            this.name = "USER";
        }
    }
}
