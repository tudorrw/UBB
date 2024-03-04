package org.example.view;

import org.example.controller.AdminController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminView {
    private AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void displayMenu() {
        System.out.println("PLease enter an option!");
        System.out.println("1 - See all users");
        System.out.println("2 - Delete users");
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
                        System.out.println("see all users");

                        break;
                    case 2:
                        System.out.println("delete users");
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

}
