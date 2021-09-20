package nc.backend.entities;


import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class UserTaskPK implements Serializable {
    @Getter
    @Setter
    @Column(name = "USER_ID")
    @NotNull
    private Long userId;

    @Getter
    @Setter
    @Column(name = "TASK_ID")
    @NotNull
    private Long taskId;

    @Getter
    @Setter
    @Column(name = "ATTEMPT_NUMBER")
    private Long attempt_number;

    public UserTaskPK(Long userId, Long taskId, Long attempt_number) {
        this.userId = userId;
        this.taskId = taskId;
        this.attempt_number = attempt_number;
    }

    //public UserTaskPK(Long userId, Long taskId) {
    //    this.userId = userId;
    //    this.taskId = taskId;
    //}
}
