package org.example.view;

import org.example.controller.MarketController;
import org.example.model.Market;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MarketView {
    private final MarketController marketController;

    public MarketView(MarketController marketController) {
        this.marketController = marketController;
    }
    public void add() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a market name: ");
        String marketName = input.nextLine();
        if (marketName.isEmpty()) {
            System.out.println("Invalid market name!");
            return;
        }

        System.out.print("Enter market location: ");
        String location = input.nextLine();
        if(location.isEmpty()) {
            System.out.println("Invalid market location!");
        }

        if (!marketController.addMarket(marketName, location)) {
            System.out.println("Invalid Market!");
        }
        else {
            System.out.println("Market added succesfully");
        }
    }
    public void getAllMarkets() {
        List<Market> markets = marketController.getAll();
        markets.stream().map(Object::toString).forEach(System.out::println);
    }
    public void deleteMarket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Give the name of the market you want to delete: ");
        String marketName = input.nextLine();
        if (marketName.isEmpty()) {
            System.out.println("Invalid market name!");
            return;
        }
        if(!marketController.removeMarket(marketName)){
            System.out.println("This market doesn't exist in our database!");
        }
        else {
            System.out.println("Market successfully removed!");
        }
    }
}
