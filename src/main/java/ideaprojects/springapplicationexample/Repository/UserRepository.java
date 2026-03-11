package ideaprojects.springapplicationexample.Repository;

import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleInOrderById(Iterable<UserRole> roles);

    @Modifying
    @Query("update User set role = :role where id = :id")
    void updateUserRole(int id, @Param("role") UserRole role);
}
