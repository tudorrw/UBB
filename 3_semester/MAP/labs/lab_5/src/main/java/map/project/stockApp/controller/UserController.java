package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.UserRequest;
import map.project.stockApp.model.Role;
import map.project.stockApp.model.User;
import map.project.stockApp.service.AdminService;
import map.project.stockApp.service.UserService;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements IPersonController {
    private final UserService userService;
    private final CredentialChecker credentialChecker;

    @Autowired
    public UserController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.credentialChecker = new CredentialChecker(userService, adminService);
    }


    @Override
    public void run() {

    }

    @GetMapping
    @ResponseBody
    List<User> seeUsers(@RequestHeader("Authorization") String authorizationHeader) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            return userService.showAllUsers();
        }
        return null;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getId(), userRequest.getUsername(), userRequest.getPassword());
        userService.createUser(user);
        return "redirect:/users";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            userService.deleteUser(id);
        }
        return "redirect:/users";
    }

    @PutMapping(path = "/update/{id}")
    public String updateUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody UserRequest userRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            userService.updateUser(id, userRequest.getUsername(), userRequest.getPassword());
        }
        return "redirect:/users";
    }


}
