package org.example.view;


import org.example.controller.*;
import org.example.utils.factory.PersonViewFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    UserController userController;
    AdminController adminController;

    ValueStockController valueStockController;
    GrowthStockController growthStockController;
    CompanyController companyController;
    MarketController marketController;

    public View(UserController userController, AdminController adminController, ValueStockController valueStockController, GrowthStockController growthStockController, CompanyController companyController, MarketController marketController) {
        this.userController = userController;
        this.adminController = adminController;
        this.valueStockController = valueStockController;
        this.growthStockController = growthStockController;
        this.companyController = companyController;
        this.marketController = marketController;
    }

    public void displayMenu() {
        System.out.println("Choose a role");
        System.out.println("1 - User");
        System.out.println("2 - Admin");
        System.out.println("3 - Exit");

    }
    public void run() {
        Scanner input = new Scanner(System.in);
        int selection = 0;

        while (selection != 3) {
            displayMenu();
            try {
                selection = input.nextInt();
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
                input.next();
            }
        }
    }

}
