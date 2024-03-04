package org.example.view;

import org.example.controller.*;
import org.example.model.*;
import org.example.utils.strategy.GrowthStockValuationStrategy;
import org.example.utils.strategy.StockProfit;
import org.example.utils.strategy.ValueStockValuationStrategy;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StockView {
    private final ValueStockController valueStockController;
    private final GrowthStockController growthStockController;
    private final MarketController marketController;
    private final CompanyController companyController;
    public StockProfit stockProfit= new StockProfit();

    public StockView(ValueStockController valueStockController, GrowthStockController growthStockController, MarketController marketController, CompanyController companyController) {
        this.valueStockController = valueStockController;
        this.growthStockController = growthStockController;
        this.marketController = marketController;
        this.companyController = companyController;
    }

    public void addValue() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a value stock id: ");
        int stockid = input.nextInt();
        input.nextLine();
        System.out.print("Enter value stock Name: ");
        String stockName = input.nextLine();
        if (stockName.isEmpty()) {
            System.out.println("Invalid value stock name!");
        }

        System.out.print("Enter company Name: ");
        String company = input.nextLine();
        if (company.isEmpty()) {
            System.out.println("Invalid company name!");
        }

        Company company1 = companyController.searchCompany(company);

        System.out.print("Enter market Name: ");
        String market = input.nextLine();
        if (market.isEmpty()) {
            System.out.println("Invalid market name!");
        }

        Market market1 = marketController.searchMarket(market);
        System.out.print("Enter value stock Dividend: ");
        double dividend = input.nextDouble();
        input.nextLine();

        if (!valueStockController.addValueStock(stockid, stockName, company1, market1, dividend)) {
            System.out.println("Invalid Value Stock!");
        } else {
            System.out.println("Value Stock added succesfully");
        }
    }

    public void addGrowth() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a growth stock id: ");
        int stockid = input.nextInt();
        input.nextLine();
        System.out.print("Enter growth stock Name: ");
        String stockName = input.nextLine();
        if (stockName.isEmpty()) {
            System.out.println("Invalid stock name!");
        }

        System.out.print("Enter company Name: ");
        String company = input.nextLine();
        if (company.isEmpty()) {
            System.out.println("Invalid company name!");
        }

        Company company1 = companyController.searchCompany(company);
        if(company1 == null) {
            System.out.println("Inexistent Company!");
            return;
        }
        System.out.print("Enter market Name: ");
        String market = input.nextLine();
        if (market.isEmpty()) {
            System.out.println("Invalid market name!");
        }

        Market market1 = marketController.searchMarket(market);
        if(market1 == null) {
            System.out.println("Inexistent Company!");
            return;
        }
        System.out.print("Enter growth stock rate: ");
        int rate = input.nextInt();
        input.nextLine();
        if (!growthStockController.addGrowthStock(stockid, stockName, company1, market1, rate)) {
            System.out.println("Invalid Growth Stock!");
        } else {
            System.out.println("Growth Stock added succesfully");
        }
    }

    public void getAllGrowthStocksDetails() {
        List<GrowthStock> growthStocks = growthStockController.getAll();
        growthStocks.stream().map(Object::toString).forEach(System.out::println);
    }

    public void getAllValueStocksDetails() {
        List<ValueStock> valueStocks = valueStockController.getAll();
        valueStocks.stream().map(Object::toString).forEach(System.out::println);
    }

    public void seeGrowthProfit() {

        stockProfit.setStrategy(new GrowthStockValuationStrategy());
        List<GrowthStock> growthStocks = growthStockController.getAll();
        for(GrowthStock growthStock: growthStocks) {

            double profit = stockProfit.calculateProfit(growthStock);
            System.out.println(growthStock.getName() + " " + profit);
        }
    }

    public void seeValueProfit() {

        stockProfit.setStrategy(new ValueStockValuationStrategy());
        List<ValueStock> valueStocks = valueStockController.getAll();
        for(ValueStock valueStock: valueStocks) {

            double profit = stockProfit.calculateProfit(valueStock);
            System.out.println(valueStock.getName() + " " + profit);
        }
    }
    public void generalValueProfit() {
        List<ValueStock> valueStocks = valueStockController.getAll();
        for(ValueStock valueStock: valueStocks) {

            double profit = stockProfit.calculateProfit(valueStock);
            System.out.println(valueStock.getName() + " " + profit);
        }
    }
    public void generalGrowthProfit() {

        List<GrowthStock> growthStocks = growthStockController.getAll();
        for(GrowthStock growthStock: growthStocks) {

            double profit = stockProfit.calculateProfit(growthStock);
            System.out.println(growthStock.getName() + " " + profit);
        }
    }

}
