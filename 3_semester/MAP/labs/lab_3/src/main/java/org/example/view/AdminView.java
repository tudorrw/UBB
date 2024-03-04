package org.example.view;

import org.example.controller.*;
import org.example.utils.strategy.GrowthStockValuationStrategy;
import org.example.utils.strategy.ValueStockValuationStrategy;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminView implements IPersonView {
    private final AdminController adminController;
    private final UserController userController;
    private final ValueStockController valueStockController;
    private final GrowthStockController growthStockController;
    private final CompanyController companyController;
    private final MarketController marketController;


    public AdminView(AdminController adminController, UserController userController, ValueStockController valueStockController, GrowthStockController growthStockController, CompanyController companyController, MarketController marketController) {
        this.adminController = adminController;
        this.userController = userController;
        this.valueStockController = valueStockController;
        this.growthStockController = growthStockController;
        this.companyController = companyController;
        this.marketController = marketController;
    }

    public void displayMenu() {
        System.out.println("Please enter an option!");
        System.out.println("1 - Manage users");
        System.out.println("2 - Manage stocks");
        System.out.println("3 - Manage companies");
        System.out.println("4 - Manage market");
        System.out.println("5 - Exit");
    }

    private void displayMenuForUsers() {
        System.out.println("1 - Add user");
        System.out.println("2 - Delete user");
        System.out.println("3 - See all users");
        System.out.println("4 - Update user");
        System.out.println("5 - Back to main menu");
    }

    private void displayMenuForStocks() {
        System.out.println("1 - Add value stock");
        System.out.println("2 - Add growth stock");
        System.out.println("3 - See all value stocks");
        System.out.println("4 - See all growth stocks");
        System.out.println("5 - Update stock");
        System.out.println("6 - See profit for value stocks");
        System.out.println("7 - See profit for growth stocks");
        System.out.println("8 - Back to main menu");
    }

    private void displayMenuForCompanies() {
        System.out.println("1 - Add company");
        System.out.println("2 - Delete company");
        System.out.println("3 - See all companies");
        System.out.println("4 - Update company");
        System.out.println("5 - Back to main menu");
    }

    private void displayMenuForMarket() {
        System.out.println("1 - Add market");
        System.out.println("2 - Delete market");
        System.out.println("3 - See all markets");
        System.out.println("4 - Update market");
        System.out.println("5 - Back to main menu");
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        int selection = 0;

        while (selection != 5) {
            displayMenu();
            try {
                selection = input.nextInt();

                switch (selection) {
                    case 1:
                        handleUserMenu();
                        break;
                    case 2:
                        handleStocksMenu();
                        break;
                    case 3:
                        handleCompaniesMenu();
                        break;
                    case 4:
                        handleMarketMenu();
                        break;
                    case 5:
                        System.out.println("Exiting admin menu.");
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

    private void handleUserMenu() {
        Scanner input = new Scanner(System.in);
        int adminSelection = 0;
        UserView userView = new UserView(userController);
        while (adminSelection != 5) {
            displayMenuForUsers();
            try {
                adminSelection = input.nextInt();
                switch (adminSelection) {
                    case 1:
                        System.out.println("Add user functionality");
                        userView.register();

                        break;
                    case 2:
                        System.out.println("Delete user functionality");
                        userView.deleteUserForAdmin();
                        break;
                    case 3:
                        System.out.println("See all users functionality");
                        userView.getAllUsers();
                        break;
                    case 4:
                        System.out.println("Update user functionality");
                        // Implement update user functionality
                        break;
                    case 5:
                        System.out.println("Back to main menu.");
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

    private void handleStocksMenu() {
        Scanner input = new Scanner(System.in);
        int adminSelection = 0;
        StockView stockView = new StockView(valueStockController, growthStockController, marketController, companyController);
        while (adminSelection != 8) {
            displayMenuForStocks();
            try {
                adminSelection = input.nextInt();

                switch (adminSelection) {
                    case 1:
                        System.out.println("Add value stock functionality");
                        stockView.addValue();
                        break;
                    case 2:
                        System.out.println("Add growth stock functionality");
                        stockView.addGrowth();
                        break;
                    case 3:
                        System.out.println("See all value stocks functionality");
                        // Implement see all users functionality
                        stockView.getAllValueStocksDetails();
                        break;
                    case 4:
                        System.out.println("See all growth stocks functionality");
                        stockView.getAllValueStocksDetails();
                        break;
                    case 5:
                        System.out.println("Update stocks functionality");
                        // Implement update user functionality
                        break;
                    case 6:
                        System.out.println("See profit for value stocks");
                        stockView.stockProfit.setStrategy(new ValueStockValuationStrategy());
                        stockView.generalValueProfit();
                        break;
                    case 7:
                        System.out.println("See profit for growth stocks");
                        stockView.stockProfit.setStrategy(new GrowthStockValuationStrategy());
                        stockView.generalGrowthProfit();
                        break;
                    case 8:
                        System.out.println("Back to main menu.");
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

    private void handleCompaniesMenu() {
        Scanner input = new Scanner(System.in);
        int adminSelection = 0;
        CompanyView companyView = new CompanyView(companyController);
        while (adminSelection != 5) {
            displayMenuForCompanies();
            try {
                adminSelection = input.nextInt();

                switch (adminSelection) {
                    case 1:
                        System.out.println("Add company functionality");
                        companyView.add();
                        break;
                    case 2:
                        System.out.println("Delete company functionality");
                        companyView.deleteCompany();
                        break;
                    case 3:
                        System.out.println("See all companies functionality");
                        companyView.getAllCompanies();
                        break;
                    case 4:
                        System.out.println("Update company functionality");
                        companyView.updateCompany();
                        break;
                    case 5:
                        System.out.println("Back to main menu.");
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

    private void handleMarketMenu() {
        Scanner input = new Scanner(System.in);
        int adminSelection = 0;
        MarketView marketView = new MarketView(marketController);
        while (adminSelection != 5) {
            displayMenuForMarket();
            try {
                adminSelection = input.nextInt();

                switch (adminSelection) {
                    case 1:
                        System.out.println("Add market functionality");
                        marketView.add();
                        break;
                    case 2:
                        System.out.println("Delete market functionality");
                        marketView.deleteMarket();
                        break;
                    case 3:
                        System.out.println("See all markets functionality");
                        marketView.getAllMarkets();
                        break;
                    case 4:
                        System.out.println("Update market functionality");
                        // Implement update market functionality
                        break;
                    case 5:
                        System.out.println("Back to main menu.");
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
