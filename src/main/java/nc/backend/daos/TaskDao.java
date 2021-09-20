package nc.backend.daos;

import nc.backend.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends CrudRepository<Task, Long> {
    @Query("SELECT task FROM Task task WHERE task.id = :id")
    Task findByID(@Param("id") Long id);

    @Query("SELECT task FROM Task task WHERE task.id > 0 AND task.id IS NOT NULL")
    List<Task> findAllTasks();
}

