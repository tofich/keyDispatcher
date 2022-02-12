package aa.trusov.keyDispatcher.services;

import aa.trusov.keyDispatcher.entities.User;
import aa.trusov.keyDispatcher.entities.UserWithoutPassword;
import aa.trusov.keyDispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    public List<UserWithoutPassword> getAllUsersWithoutPassword(){
        List<UserWithoutPassword> users = (List<UserWithoutPassword>) userRepository.findAllWithoutPassword();
        return users;
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Long getCountUsers(){
        return userRepository.count();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void changeUserActive(boolean active, Long id){
        userRepository.changeUserActive(active, id);
    }

    public void deleteById(Long id){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

}
