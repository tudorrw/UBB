package dbms.concurrency.backend.javaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/java-concurrency")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/dirty_read")
    public ResponseEntity<User> dirty_read(@RequestParam Integer userId) throws InterruptedException {
        Optional<User> user = userService.getUserById(userId);
        String before = "Before Transaction: " + (user.isPresent() ? user.get().toString() : "User not found");
        if(user.()) {

        }
    }

}
