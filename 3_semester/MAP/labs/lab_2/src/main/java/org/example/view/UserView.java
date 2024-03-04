package org.example.view;

import org.example.controller.UserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void displayMenu() {
        System.out.println("Welcome to the best stock app on the planet!");
        System.out.println("Please register or log in");
        System.out.println("1 - Register");
        System.out.println("2 - Log In");
        System.out.println("3 - Exit");
    }
    public void run() {
        Scanner input = new Scanner(System.in);
        int selection = 0;

        while (selection != 3) {
            displayMenu();
            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1:
                        register();
                        break;
                    case 2:
                        logIn();
                        break;
                    case 3:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }
    public void register() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String username = input.nextLine();
        if (username.isEmpty()) {
            System.out.println("Invalid username!");
            return;
        }

        System.out.print("Enter a password: ");
        String password = input.nextLine();
        if(password.isEmpty()) {
            System.out.println("Invalid password!");
            return;
        }

        System.out.print("Enter the password again: ");
        String passwordConfirmation = input.nextLine();
        if(passwordConfirmation.isEmpty()) {
            System.out.println("Invalid password confirmation!");
            return;
        }

        if(!password.equals(passwordConfirmation)) {
            System.out.println("passwords given do not match");
            return;
        }
        if(userController.createUser(username, password)) {
            System.out.println("Registered!");
            dashboard(username);
        }
        else {
            System.out.println("This username already exists!");
        }
    }
    public void logIn() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String username = input.nextLine();
        if (username.isEmpty()) {
            System.out.println("Invalid username!");
            return;
        }

        System.out.print("Enter a password: ");
        String password = input.nextLine();
        if(password.isEmpty()) {
            System.out.println("Invalid password!");
            return;
        }
        if(userController.findUser(username, password) != null) {
            System.out.println("Logged in!");
            dashboard(username);
        }
        else {
            System.out.println("Login failed. Wrong username or password!");
        }
    }

    private void dashboardMenu() {
        System.out.println("1. Search Stock");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. Transaction History");
        System.out.println("5. Change password");
        System.out.println("6. Log out");
        System.out.println("7. Delete account");
    }
    private void dashboard(String username) {
        Scanner input = new Scanner(System.in);
        int selection = 0;

        while (selection != 6) {
            dashboardMenu();
            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1:
                        System.out.println("implement search stock");
                        userController.showAllUsers();
                        break;
                    case 2:
                        System.out.println("implement buy stock");
                        break;
                    case 3:
                        System.out.println("implement sell stock");
                        break;
                    case 4:
                        System.out.println("implement transaction history");
                        break;
                    case 5:
                        System.out.println("implement change password");
                        break;
                    case 6:
                        System.out.println("logged out");
                        break;
                    case 7:
                        System.out.println("Do you want to delete your account?\nEnter your password to confirm");

                        Scanner option = new Scanner(System.in);
                        String password = option.nextLine();
                        if(userController.deleteUser(username, password)){
                            System.out.println("Account deleted successfully!");
                            return;
                        }
                        else {
                            System.out.println("Invalid input. Please try again.");
                        }
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }
}
