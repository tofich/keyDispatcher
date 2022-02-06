package aa.trusov.keyDispatcher.repositories;

import aa.trusov.keyDispatcher.entities.User;
import aa.trusov.keyDispatcher.entities.UserWithoutPassword;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    public User findByUsername(String username);

    @Query("SELECT u FROM UserWithoutPassword u")
    public List<UserWithoutPassword> findAllWithoutPassword();

    @Modifying
    @Query("UPDATE UserWithoutPassword u SET u.active = ?1 WHERE u.id = ?2")
    void changeUserActive(boolean active, Long id);

}
