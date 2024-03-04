package map.project.stockApp.utils.autorization;

import map.project.stockApp.model.Admin;
import map.project.stockApp.model.Role;
import map.project.stockApp.model.User;
import map.project.stockApp.service.AdminService;
import map.project.stockApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialChecker {


    private final UserService userService;
    @Autowired
    private AdminService adminService;


    @Autowired
    public CredentialChecker(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    public Boolean checkCredentials(String credentials, Role role) {
        if (credentials != null && credentials.startsWith("Credentials")) {
            // Extract the username and password from the Authorization header
            credentials = credentials.substring("Credentials".length()).trim();

            // Split the credentials into username and password
            String[] credentialsArray = credentials.split(",");
            System.out.println(credentialsArray);
            if (credentialsArray.length == 2) {
                String username = credentialsArray[0].trim();
                String password = credentialsArray[1].trim();

                System.out.println(username);
                System.out.println(password);

                if (role == Role.User) {
                    if (userService.findUser(username, password) != null) {
                        return true;
                    }
                }
                Admin admin = adminService.findAdmin(username, password);
                if (Role.valueOf(admin.getRole()) == Role.Super_Admin) {
                    if (adminService.findAdmin(username, password) != null) {
                        return true;
                    }
                }
                if (role == Role.Admin) {
                    if (adminService.findAdmin(username, password) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public User returnUser(String credentials) {
        if (credentials != null && credentials.startsWith("Credentials")) {
            // Extract the username and password from the Authorization header
            credentials = credentials.substring("Credentials".length()).trim();

            // Split the credentials into username and password
            String[] credentialsArray = credentials.split(",");

            if (credentialsArray.length == 2) {
                String username = credentialsArray[0].trim();
                String password = credentialsArray[1].trim();

                System.out.println(username);
                System.out.println(password);

                User user = userService.findUser(username, password);
                if (user != null) {
                    return user;
                }
            }
        }
        return null;
    }
}