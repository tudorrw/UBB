package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.PortfolioRequest;
import map.project.stockApp.controller.requests.StockRequest;
import map.project.stockApp.model.*;
import map.project.stockApp.service.*;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final CredentialChecker credentialChecker;
    private final TransactionService transactionService;

    private final UserService userService;
    private final StockService stockService;

    public PortfolioController(PortfolioService portfolioService, UserService userService, StockService stockService, AdminService adminService, TransactionService transactionService) {
        this.portfolioService = portfolioService;
        this.credentialChecker = new CredentialChecker(userService, adminService);
        this.userService = userService;
        this.stockService = stockService;
        this.transactionService = transactionService;
    }

    @GetMapping("/portfolios")
    @ResponseBody
    List<Portfolio> seePortfolios(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            return portfolioService.getAll();
        }
        return null;
    }


    // fiecare portfolio ar fi lejer sa aiba id-ul egal cu id-ul de la user
    @PostMapping("/portfolios/add")
    public String addPortfolio(@RequestBody PortfolioRequest portfolioRequest) {
        Portfolio portfolio = new Portfolio(this.userService.findUser(portfolioRequest.getUser()).getId(), this.userService.findUser(portfolioRequest.getUser()), portfolioRequest.getCash(), new ArrayList<>());

        List<Integer> stockQuantities = portfolioRequest.getStockQuantities();
        List<Stock> stocks = portfolioRequest.getStocks().stream()
                .map(stockService::findStock)
                .collect(Collectors.toList());

        List<PortfolioStockLink> portfolioStockLinks = new ArrayList<>();

        // Assuming both lists have the same size
        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            int quantity = stockQuantities.get(i);

            PortfolioStockLink link = new PortfolioStockLink();
            link.setPortfolio(portfolio);
            link.setStock(stock);
            link.setQuantity(quantity);

            portfolioStockLinks.add(link);
        }
        portfolio.setPortfolioStockLinks(portfolioStockLinks);
        portfolioService.addPortfolioObject(portfolio);
        return "redirect:/portfolios";
    }

    @DeleteMapping(path = "/portfolios/delete/{id}")
    public String deletePortfolio(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            portfolioService.deletePortfolio(id);
        }
        return "redirect:/portfolios";
    }

    @PutMapping("/portfolios/update/{id}")
    public String updatePortfolio(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody PortfolioRequest portfolioRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            portfolioService.updatePortfolio(this.userService.findUser(portfolioRequest.getUser()).getId(), this.userService.findUser(portfolioRequest.getUser()), portfolioRequest.getCash(), portfolioRequest.getStocks().stream()
                    .map(stockService::findStock)
                    .collect(Collectors.toList()), portfolioRequest.getStockQuantities());
        }
        return "redirect:/portfolios";
    }


    @GetMapping("/portfolio")
    @ResponseBody
    String seePortfolio(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        if (credentialChecker.checkCredentials(authorizationHeader, Role.User)) {
            return portfolioService.getByUser(credentialChecker.returnUser(authorizationHeader)).toString();
        }
        return null;
    }


    @GetMapping("/portfolio/seeValue")
    @ResponseBody
    String seePortfolioValue(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        if (credentialChecker.checkCredentials(authorizationHeader, Role.User)) {
            Double portfolioValue = portfolioService.calculateValue(portfolioService.getByUser(credentialChecker.returnUser(authorizationHeader)));
            return "Total: " + portfolioValue;
        }
        return null;
    }


    @PostMapping("/portfolio/buyStock")
    public String buyStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StockRequest buyStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.User)) {
            User user = credentialChecker.returnUser(authorizationHeader);
            Stock stock = this.stockService.findStock(buyStockRequest.getStockName());
            System.out.println(stock);
            String message = (portfolioService.buyStock(user, stock, buyStockRequest.getStockQuantity()));
            System.out.println(message);
            if (Objects.equals(message, "Action Completed")) {
                transactionService.addTransaction(user, stock, LocalDateTime.now(), buyStockRequest.getStockQuantity(), "Buy");
            }
            return message;
        }
        return "Action Not Completed";
    }

    @PostMapping(path = "/portfolio/sellStock")
    public String sellStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StockRequest sellStockRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.User)) {
            User user = credentialChecker.returnUser(authorizationHeader);
            Stock stock = this.stockService.findStock(sellStockRequest.getStockName());
            String message = portfolioService.sellStock(credentialChecker.returnUser(authorizationHeader), this.stockService.findStock(sellStockRequest.getStockName()), sellStockRequest.getStockQuantity());
            System.out.println(message);
            if (Objects.equals(message, "Action Completed")) {
                transactionService.addTransaction(user, stock, LocalDateTime.now(), sellStockRequest.getStockQuantity(), "Sell");
            }
            return message;
        }
        return "redirect:/portfolio";
    }

}