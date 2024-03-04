package map.project.stockApp.service;

import map.project.stockApp.model.Portfolio;
import map.project.stockApp.model.PortfolioStockLink;
import map.project.stockApp.model.Stock;
import map.project.stockApp.model.User;
import map.project.stockApp.repo.springRepo.PortfolioRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepositorySpring repository;

    public PortfolioService() {

    }

    public boolean addPortfolio(int id, User user, int cash, List<PortfolioStockLink> stocks) {
        if (!this.searchPortfolioBool(id)) {
            repository.save(new Portfolio(id, user, cash, stocks));
            return true;
        } else {
            return false;
        }
    }

    public boolean removePortfolio(int id) {
        Portfolio search = this.searchPortfolio(id);
        if (search != null) {
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }

    public List<Portfolio> getAll() {
        return repository.findAll();
    }

    public String getByUserString(User user) {
        Portfolio userPortfolio = repository.findByUser(user);
        System.out.println(userPortfolio);
        if (userPortfolio == null) {
            this.addPortfolioObject(new Portfolio(user.getId(), user, 10000, new ArrayList<>()));
            return repository.findByUser(user).toString();
        } else {
            return userPortfolio.toString();
//            return null;
            //Infinite
            //return userPortfolio;
        }
    }

    public Portfolio getByUser(User user) {
        Portfolio userPortfolio = repository.findByUser(user);
        System.out.println(userPortfolio);
        if (userPortfolio == null) {
            this.addPortfolioObject(new Portfolio(user.getId(), user, 10000, new ArrayList<>()));
            return repository.findByUser(user);
        } else {
            return userPortfolio;
        }
    }

    public boolean searchPortfolioBool(int id) {
        Portfolio portfolio = repository.findById(id).orElse(null);
        if (portfolio != null) {
            return true;
        }
        return false;
    }

    public Portfolio searchPortfolio(int id) {
        Portfolio portfolio = repository.findById(id).orElse(null);
        if (portfolio != null) {
            return portfolio;
        }
        return null;
    }


    public void addPortfolioObject(Portfolio portfolio) {
        if (!repository.existsById(portfolio.getId())) {
            repository.save(portfolio);
        }
    }

    public void deletePortfolio(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updatePortfolio(int id, User newUser, double newCash, List<Stock> newStocks, List<Integer> newQuantities) {
        Portfolio existingPortfolio = repository.findById(id).orElse(null);
        if (existingPortfolio != null) {
            existingPortfolio.setUser(newUser);
            existingPortfolio.setCash(newCash);

            List<Integer> stockQuantities = newQuantities;
            List<Stock> stocks = newStocks;

            List<PortfolioStockLink> portfolioStockLinks = new ArrayList<>();

            // Assuming both lists have the same size
            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                int quantity = stockQuantities.get(i);

                PortfolioStockLink link = new PortfolioStockLink();
                link.setPortfolio(existingPortfolio);
                link.setStock(stock);
                link.setQuantity(quantity);

                portfolioStockLinks.add(link);
            }
            existingPortfolio.setPortfolioStockLinks(portfolioStockLinks);

            repository.save(existingPortfolio);
        }
    }

    public String buyStock(User user, Stock stock, int stockQuantity) {
        Portfolio existingPortfolio = this.getByUser(user);
        if (existingPortfolio != null) {
            if (existingPortfolio.getCash() >= stockQuantity * stock.getPrice()) {
                if (stock.getCompany().getNumber_shares_outstanding() >= stockQuantity) {
                    existingPortfolio.setCash(existingPortfolio.getCash() - stockQuantity * stock.getPrice());
                    System.out.println(stockQuantity * stock.getPrice());
                } else {
                    return "Not enough shares";
                }
            } else {
                return "Not enough cash";
            }

            System.out.println("Checkpoint");
            existingPortfolio.addStock(stock, stockQuantity);
            System.out.println("Checkpoint22");
            /////////////////// dont forget
            repository.save(existingPortfolio);
            /////////////
            return "Action Completed";
        }

        return "Action not completed";
    }

    public String sellStock(User user, Stock stock, int stockQuantity) {
        Portfolio existingPortfolio = this.getByUser(user);
        if (existingPortfolio != null) {
            PortfolioStockLink stockLink = existingPortfolio.findStockLink(stock);
            if (stockLink != null) {
                if (stockLink.getQuantity() >= stockQuantity) {
                    existingPortfolio.setCash(existingPortfolio.getCash() + stockQuantity * stock.getPrice());

                    existingPortfolio.removeStock(stock, stockQuantity);

                    /////////////////// dont forget
                    repository.save(existingPortfolio);
                    /////////////

                    return "Action Completed";


                }
            }
            return "Not enough shares";
        }

        return "Action not completed";
    }

    public double calculateValue(Portfolio portfolio) {
        double value = portfolio.getCash();
        for (PortfolioStockLink portfolioStockLink: portfolio.getPortfolioStockLinks()){
            value+=portfolioStockLink.getStock().getPrice()*portfolioStockLink.getQuantity();
        }

        return value;
    }
}
