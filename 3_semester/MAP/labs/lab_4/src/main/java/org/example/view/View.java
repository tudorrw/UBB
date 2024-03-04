package org.example.view;


import org.example.controller.*;
import org.example.repo.*;
import org.example.utils.factory.PersonViewFactory;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class View {
    UserController userController;
    AdminController adminController;

    ValueStockController valueStockController;
    GrowthStockController growthStockController;
    CompanyController companyController;
    MarketController marketController;
    private static View instance;
    private final Scanner scanner;

    private View() {
        this.scanner = new Scanner(System.in);
    }

    public static View getInstance() {
        if(instance == null) {
            instance = new View();
        }
        return instance;
    }

    public void displayRepoTypeMenu() {
        System.out.println("Choose repo type:");
        System.out.println("1. Database");
        System.out.println("2. In memory");
        System.out.println("Enter your choice: ");
    }

    private RepoTypes selectRepoType() {
        displayRepoTypeMenu();
        String option = scanner.nextLine();
        while(!Objects.equals(option, "1") && !Objects.equals(option, "2")) {
            System.out.println("Invalid option, please enter a valid choice!");
            System.out.println("Enter your choice: ");
            option = scanner.nextLine();
        }
        if (option.equals("1")) {
            return RepoTypes.database;
        }
        return RepoTypes.inMemory;
    }

    public void displayMenu() {
        System.out.println("Choose a role");
        System.out.println("1 - User");
        System.out.println("2 - Admin");
        System.out.println("3 - Exit");

    }
    public void run() {
        selectRepoTypes();
        int selection = 0;
        while (selection != 3) {
            displayMenu();
            try {
                selection = scanner.nextInt();
                IPersonView view = PersonViewFactory.createView(selection, userController, adminController, valueStockController, growthStockController,companyController, marketController);
                if(view != null) {
                    view.run();
                }
                else {
                    if(selection == 3){
                        System.out.println("closing app...");
                    }
                    else {
                        throw new InputMismatchException();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    private void selectRepoTypes() {
        RepoTypes repoType = selectRepoType();

        adminController.setRepoType(repoType);
        adminController = AdminController.getInstance();

        userController.setRepoType(repoType);
        userController = UserController.getInstance();

        growthStockController.setRepoType(repoType);
        growthStockController = GrowthStockController.getInstance();

        valueStockController.setRepoType(repoType);
        valueStockController = ValueStockController.getInstance();

        companyController.setRepoType(repoType);
        companyController = CompanyController.getInstance();

        marketController.setRepoType(repoType);
        marketController = MarketController.getInstance();
    }
}
