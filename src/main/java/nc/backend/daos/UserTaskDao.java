package nc.backend.daos;

import nc.backend.entities.UserTask;
import nc.backend.entities.UserTaskPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTaskDao extends CrudRepository<UserTask, UserTaskPK> {
    @Query("SELECT ut FROM UserTask ut")
    List<UserTask> findAllBy();

    @Query("SELECT userTask FROM UserTask userTask WHERE" +
            " userTask.user.id = :userId AND userTask.task.id = :taskId")
    List<UserTask> findByUserIdAndTaskId(@Param("userId") Long userId,
                                         @Param("taskId") Long taskId);
}
