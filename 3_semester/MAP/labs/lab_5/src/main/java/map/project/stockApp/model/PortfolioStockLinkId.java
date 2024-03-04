package map.project.stockApp.model;

import java.io.Serializable;

public class PortfolioStockLinkId implements Serializable {

    private Integer portfolio;
    private Integer stock;

    public PortfolioStockLinkId() {
    }

    public PortfolioStockLinkId(Integer portfolio, Integer stock) {
        this.portfolio = portfolio;
        this.stock = stock;
    }

    public Integer getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Integer portfolio) {
        this.portfolio = portfolio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortfolioStockLink that = (PortfolioStockLink) o;

        if (portfolio != null ? !portfolio.equals(that.getPortfolio()) : that.getPortfolio() != null) return false;
        return stock != null ? stock.equals(that.getStock()) : that.getStock() == null;
    }

    @Override
    public int hashCode() {
        int result = portfolio != null ? portfolio.hashCode() : 0;
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        return result;
    }


}

