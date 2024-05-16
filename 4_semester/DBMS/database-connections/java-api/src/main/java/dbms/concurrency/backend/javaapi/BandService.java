package dbms.concurrency.backend.javaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User dirty_read(Integer id) throws InterruptedException{
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
           Thread.sleep(5000);
        }
        return userRepository.findById(id).orElse(null);
    }

}
