package org.example;

import org.example.controller.*;
import org.example.model.*;
import org.example.repo.*;
import org.example.view.StockView;
import org.example.view.View;

public class Main {
    public static void main(String[] args) {
        IRepository<User> userRepo = UserRepository.getInstance();
        IRepository<Admin> adminRepo = AdminRepository.getInstance();
        IRepository<Company> companyRepo = CompanyRepository.getInstance();
        IRepository<Market> marketRepo = MarketRepository.getInstance();
        IRepository<ValueStock> valueStockIRepository = ValueStockRepository.getInstance();
        IRepository<GrowthStock> growthStockIRepository = GrowthStockRepository.getInstance();
        UserController userController = new UserController(userRepo);
        AdminController adminController = new AdminController(adminRepo);
        CompanyController companyController = new CompanyController(CompanyRepository.getInstance());
        MarketController marketController = new MarketController(marketRepo);
        GrowthStockController growthStockController = new GrowthStockController(growthStockIRepository);
        ValueStockController valueStockController = new ValueStockController(valueStockIRepository);
        View view = new View(userController, adminController, valueStockController, growthStockController,companyController, marketController);
        view.run();
    }
}