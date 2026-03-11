package ideaprojects.springapplicationexample.service;

import ideaprojects.springapplicationexample.Repository.UserRepository;
import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getCurrentUser(){
         String email = SecurityContextHolder.getContext().getAuthentication().getName(); // todo give me current user information with Spring Boot helping

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User this email: " + email + "not found" ));
    }

    public List<User> findAllByRoleIn(Iterable<UserRole> roles){
        return  userRepository.findAllByRoleInOrderById(roles);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public void updateRole(int id, UserRole role){
        userRepository.updateUserRole(id, role);
    }
}
