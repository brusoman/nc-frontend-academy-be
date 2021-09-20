package nc.backend.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@ToString
@NoArgsConstructor
public class Task {
    @Id
    @Column(name  = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "NUMBER")
    @NotNull
    private Integer number;

    @Column(name = "SECTION")
    @NotNull
    private String section;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_PATH")
    private String file_path;

    @Column(name = "ATTEMPTS_MAX")
    private Integer attempts_max;

    @Column(name = "TASK_NAME")
    private String task_name;

    @Column(name = "DEADLINE")
    private ZonedDateTime deadline;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    @Getter
    @Setter
    private List<UserTask> userTasks;

    public Task(Long id, Integer number, String section, String description,
                Integer attempts_max, String task_name,
                List<UserTask> userTasks, ZonedDateTime deadline) {
        this.id = id;
        this.number = number;
        this.section = section;
        this.description = description;
        this.attempts_max = attempts_max;
        this.task_name = task_name;
        this.userTasks = userTasks;
        this.deadline = deadline;
    }
}
