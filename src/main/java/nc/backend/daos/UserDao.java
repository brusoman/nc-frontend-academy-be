package nc.backend.daos;

import nc.backend.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    @Query("SELECT user from User user " +
            "where user.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT user FROM User user WHERE user.id = :id")
    User findByID(@Param("id") Long id);

    @Query("SELECT user FROM User user WHERE user.login = :login")
    User findByUserLogin(@Param("login") String login);

}
