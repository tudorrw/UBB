package org.example.view;

import org.example.controller.CompanyController;
import org.example.model.Company;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CompanyView {
    private final CompanyController companyController;

    public CompanyView(CompanyController companyController) {
        this.companyController = companyController;
    }
    public void add() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a company name: ");
        String companyName = input.nextLine();
        if (companyName.isEmpty()) {
            System.out.println("Invalid company name!");
            return;
        }

        long numberShares = 1;
        System.out.print("Enter company number of shares: ");
        try {
            numberShares = input.nextLong();
            System.out.println(numberShares);
        } catch(InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

        System.out.print("Enter company capitalization: ");
        try {
            long capitalization = input.nextLong();
            System.out.println(capitalization);
            if (!companyController.addCompany(companyName, capitalization, numberShares)) {
                System.out.println("Invalid capitalization!");
            }
            else {
                System.out.println("Company added succesfully");
            }
        } catch(InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    public void getAllCompanies() {
        List<Company> companies = companyController.getAll();
        companies.stream().map(Object::toString).forEach(System.out::println);
    }
    public void deleteCompany() {
        Scanner input = new Scanner(System.in);
        System.out.println("Give the name of the company you want to delete: ");
        String companyName = input.nextLine();
        if (companyName.isEmpty()) {
            System.out.println("Invalid company name!");
            return;
        }
        if(!companyController.removeCompany(companyName)){
            System.out.println("This company doesn't exist in our database!");
        }
        else {
            System.out.println("Company successfully removed!");
        }
    }

    public void updateCompany() {
        Scanner input = new Scanner(System.in);
        System.out.println("Give the name of the company you want to update: ");
        String companyName = input.nextLine();
        if (companyName.isEmpty()) {
            System.out.println("Invalid company name!");
            return;
        }

        System.out.println("What do you want to update: ");
        System.out.println("1 Capitalization");
        System.out.println("2 Number of shares");
        try {
            int selection = input.nextInt();
            switch (selection) {
                case 1:
                    try {
                        System.out.print("Enter new capitalization: ");
                        long number = input.nextLong();
                        System.out.println(number);
                        if (!companyController.updateCompany(companyName, "capitalization", number)) {
                            System.out.println("Invalid capitalization!");
                        }
                        else {
                            System.out.println("Company updated succesfully");
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }

                    break;
                case 2:
                    try {
                        System.out.print("Enter new number of shares: ");

                        long number = input.nextLong();
                        System.out.println(number);
                        if (!companyController.updateCompany(companyName, "numberShares", number)) {
                            System.out.println("Invalid number of shares!");
                        }
                        else {
                            System.out.println("Company updated succesfully");
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                    break;
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }

    }
}
