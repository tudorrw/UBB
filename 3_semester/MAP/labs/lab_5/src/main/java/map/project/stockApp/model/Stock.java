package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import map.project.stockApp.utils.memento.StockMemento;
import map.project.stockApp.utils.observer.Observer;
import map.project.stockApp.utils.strategyAndTemplateMethod.GrowthStockValuationStrategy;
import map.project.stockApp.utils.strategyAndTemplateMethod.StockValuationStrategy;

@Getter
@Entity(name = "stocks")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stock_type", discriminatorType = DiscriminatorType.STRING)
public class Stock implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(unique = true)
    protected String name;
    @Transient
    protected float price;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id")
    protected Company company;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "market_id")
    protected Market market;

    @Transient
    protected StockValuationStrategy valuationStrategy;

    public Stock() {

    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", company=" + company.getName() +
                ", market=" + market.getName() +
                '}';
    }


    public Stock(int id, String name, Company company_, Market market_) {
        this.id = id;
        this.name = name;
        this.company = company_;
        this.market = market_;
        this.company.registerObserver(this);
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    protected void setPrice() {
        this.price = (float) this.company.getCapitalization() / this.company.getNumber_shares();

    }

    @PostLoad
    public void configure() {
        this.setPrice();
        this.company.registerObserver(this);
    }


    public void setCompany(Company company_) {
        System.out.println("bbbbbbbbbbbbb");
        if (this.company != null)
            company.removeObserver(this);
        company = company_;
        this.setPrice();
        company.registerObserver(this);
    }


    public void setMarket(Market market_) {
        market = market_;
    }

    public void setValuationStrategy(StockValuationStrategy valuationStrategy) {
        this.valuationStrategy = valuationStrategy;
    }

    public double calculateValue() {
        return valuationStrategy.calculatePossibleProfit(this);
    }

    @Override
    public void update() {
        this.setPrice();
    }

    public StockMemento createMemento() {
        return new StockMemento(this);
    }


    public void restoreFromMemento(StockMemento memento) {
        this.setId(memento.getId());
        this.setName(memento.getName());
        this.setCompany(memento.getCompany());
        this.setMarket(memento.getMarket());
    }
}

