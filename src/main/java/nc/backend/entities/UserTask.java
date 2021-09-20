package nc.backend.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "USERS_TASKS")
@ToString(exclude = {"user", "task"})
@NoArgsConstructor
public class UserTask {

    @EmbeddedId
    @Getter
    @Setter
    private UserTaskPK userTaskPK = new UserTaskPK();

    @Column(name = "PROGRESS")
    @NotNull
    @Getter
    @Setter
    private BigDecimal progress;

    @Column(name = "PATH_RESULT")
    @NotNull
    @Getter
    @Setter
    private String path_result;

    @Column(name = "PATH_UPLOAD")
    @NotNull
    @Getter
    @Setter
    private String path_upload;

    @Column(name = "TIME")
    @Getter
    @Setter
    @NotNull
    private ZonedDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @Setter
    @Getter
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", insertable = false, updatable = false)
    @Setter
    @Getter
    private Task task;

    public UserTask(UserTaskPK userTaskPK, BigDecimal progress, String path_result,
                    ZonedDateTime time, User user, Task task) {
        this.userTaskPK = userTaskPK;
        this.progress = progress;
        this.path_result = path_result;
        this.time = time;
        this.user = user;
        this.task = task;
    }

    public UserTask(UserTaskPK userTaskPK, BigDecimal progress, String path_result, String path_upload,
                    ZonedDateTime time) {
        this.userTaskPK = userTaskPK;
        this.progress = progress;
        this.path_result = path_result;
        this.path_upload = path_upload;
        this.time = time;
    }

}
