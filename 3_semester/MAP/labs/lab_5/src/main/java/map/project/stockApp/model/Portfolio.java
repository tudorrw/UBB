package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity(name = "portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double cash;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PortfolioStockLink> portfolioStockLinks;

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", user=" + user +
                ", cash=" + cash +
                ", portfolioStockLinks=" + portfolioStockLinks +
                '}';
    }

    public Portfolio() {

    }


    public void addStock(Stock stock, int quantity) {
        for (PortfolioStockLink link : portfolioStockLinks) {
            if (link.getStock().equals(stock)) {
                link.setQuantity(link.getQuantity() + quantity);
                stock.getCompany().setNumber_shares_outstanding(stock.getCompany().getNumber_shares_outstanding() - quantity);
                return;
            }
        }
        PortfolioStockLink link = new PortfolioStockLink(this, stock, quantity);
        stock.getCompany().setNumber_shares_outstanding(stock.getCompany().getNumber_shares_outstanding() - quantity);
        System.out.println(link);
        portfolioStockLinks.add(link);
    }


    public Portfolio(int id, User user, double cash, List<PortfolioStockLink> portfolioStockLinks) {
        this.id = id;
        this.user = user;
        this.cash = cash;
        this.portfolioStockLinks = portfolioStockLinks;
    }


    public void removeStock(Stock stock, int quantity) {
        for (PortfolioStockLink link : portfolioStockLinks) {
            if (link.getStock().equals(stock)) {
                if (link.getQuantity() == quantity) {
                    portfolioStockLinks.remove(link);
                    link.setPortfolio(null);
                    link.setStock(null);
                    stock.getCompany().setNumber_shares_outstanding((stock.getCompany().getNumber_shares_outstanding() + quantity));
                    break;
                } else {
                    link.setQuantity(link.getQuantity() - quantity);
                    stock.getCompany().setNumber_shares_outstanding(stock.getCompany().getNumber_shares_outstanding() + quantity);
                    break;
                }
            }
        }
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void setPortfolioStockLinks(List<PortfolioStockLink> portfolioStockLinks) {
        this.portfolioStockLinks = portfolioStockLinks;
    }

    public PortfolioStockLink findStockLink(Stock stock) {
        for (PortfolioStockLink stockLink : portfolioStockLinks) {
            if (stockLink.getStock() == stock) {
                return stockLink;
            }
        }
        return null;
    }
}