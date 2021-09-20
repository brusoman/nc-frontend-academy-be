package nc.backend.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
public class User {

    @Id
    @Column(name  = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "LOGIN")
    @NotNull
    private String login;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "EMAIL")
    @NotNull
    private String email;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "SURNAME")
    @NotNull
    private String surname;

    @Column(name = "ADMIN")
    @NotNull
    private Boolean admin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Getter
    @Setter
    private List<UserTask> userTasks;

    public User(String login, String email, String name, String surname, Boolean admin, List<UserTask> userTasks) {
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
        this.userTasks = userTasks;
    }

    public User(String password, String login, String email, String name, String surname, boolean admin) {
        this.password = password;
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
    }

}
