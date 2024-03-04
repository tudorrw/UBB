package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.*;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PortfolioRepositoryDB implements IRepository<Portfolio> {
    private Connection connection = DBConnection.getConnection();

    private static PortfolioRepositoryDB instance;
    private UserRepositoryDB userRepositoryDB;
    private GrowthStockRepositoryDB growthStockRepositoryDB;
    private ValueStockRepositoryDB valueStockRepositoryDB;
    private List<Portfolio> cache;

    public PortfolioRepositoryDB() {
        this.userRepositoryDB = UserRepositoryDB.getInstance();
        this.growthStockRepositoryDB = GrowthStockRepositoryDB.getInstance();
        this.valueStockRepositoryDB = ValueStockRepositoryDB.getInstance();
        this.cache = importCache();
    }

    private List<Portfolio> importCache(){
        List<Portfolio> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Portfolios";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("portfolioId");
                User user = userRepositoryDB.searchIdCache(results.getInt("userId"));
                double cash = results.getInt("cash");

                String queryGrowthStocks = "SELECT * FROM PortfolioGrowthStockLink WHERE portfolioId = ?";
                PreparedStatement statementGrowthStocks = connection.prepareStatement(queryGrowthStocks);
                statementGrowthStocks.setInt(1, id);

                ResultSet resultsStock = statementGrowthStocks.executeQuery();
                List<Stock> stocks = new ArrayList<>();
                while(resultsStock.next()) {
                    Stock stock = growthStockRepositoryDB.searchIdCache(results.getInt("growthStockId"));
                    stocks.add(stock);
                }

                String queryValueStocks = "SELECT * FROM PortfolioValueStockLink WHERE portfolioId = ?";
                PreparedStatement statementValueStocks = connection.prepareStatement(queryValueStocks);
                statementValueStocks.setInt(1, id);

                ResultSet resultsValueStock = statementValueStocks.executeQuery();
                while(resultsValueStock.next()) {
                    Stock stock = valueStockRepositoryDB.searchIdCache(results.getInt("valueStockId"));
                    stocks.add(stock);
                }

                result.add(new Portfolio(id,user,cash,stocks));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return result;
    }


    public Portfolio searchIdCache(int id) {
        for(Portfolio portfolio: this.cache){
            if (portfolio.getId() == id){
                return portfolio;
            }
        }

        return null;
    }

    public int searchIdCachePosition(int id) {
        for(int i = 0 ;i < this.cache.size(); i++){
            if (cache.get(i).getId() == id){
                return i;
            }
        }

        return -1;
    }

    public static PortfolioRepositoryDB getInstance() {
        if(instance == null) {
            instance = new PortfolioRepositoryDB();
        }
        return instance;
    }

    @Override
    public List<Portfolio> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Portfolio portfolio) {
        int id = portfolio.getId();
        int userId = portfolio.getUser().getId();
        double cash = portfolio.getCash();
        List<Stock> stocks = portfolio.getStocks();

        String query = "INSERT INTO Portfolios (portfolioId, userId, cash) VALUES (?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.setDouble(3, cash);

            statement.executeUpdate();

            this.cache.add(portfolio);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        insertStocks(id,stocks);
    }

    @Override
    public void update(Portfolio portfolio) {
        int id = portfolio.getId();
        int userId = portfolio.getUser().getId();
        double cash = portfolio.getCash();
        List<Stock> stocks = portfolio.getStocks();

        Portfolio old_portfolio = searchIdCache(id);

        if(old_portfolio != null) {
            try {
                String query = "UPDATE Portfolios SET userId = ?, cash = ? WHERE portfolioId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(3, id);
                statement.setInt(1, userId);
                statement.setDouble(2, cash);

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            List<Stock> added = new ArrayList<>(portfolio.getStocks());
            added.removeAll(old_portfolio.getStocks());
            insertStocks(id,added);

            List<Stock> removed = new ArrayList<>(old_portfolio.getStocks());
            removed.removeAll(portfolio.getStocks());
            deleteStocks(id,removed);

            old_portfolio.setUser(portfolio.getUser());
            old_portfolio.setCash(cash);
            old_portfolio.setStocks(portfolio.getStocks());
        }

    }

    @Override
    public void delete(Portfolio object) {
        int id = object.getId();
        String query = "DELETE FROM Portfolios WHERE portfolioId = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            this.cache.remove(searchIdCachePosition(id));

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertStocks(int portfolioId, List<Stock> stocks){
        for (Stock stock: stocks){
            if(stock instanceof GrowthStock){
                String queryGrowthStock = "INSERT INTO PortfolioGrowthStockLink (portfolioId, valueStockId) VALUES (?, ?)";
                try{
                    PreparedStatement statementGrowthStock = connection.prepareStatement(queryGrowthStock);
                    statementGrowthStock.setInt(1, portfolioId);
                    statementGrowthStock.setInt(2, stock.getId());

                    statementGrowthStock.executeUpdate();


                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                String queryValueStock = "INSERT INTO PortfolioValueStockLink (portfolioId, growthStockId) VALUES (?, ?)";
                try{
                    PreparedStatement statementValueStock = connection.prepareStatement(queryValueStock);
                    statementValueStock.setInt(1, portfolioId);
                    statementValueStock.setInt(2, stock.getId());

                    statementValueStock.executeUpdate();


                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void deleteStocks(int portfolioId, List<Stock> stocks){
        for (Stock stock: stocks){
            if(stock instanceof GrowthStock){
                String queryGrowthStock = "DELETE FROM PortfolioGrowthStockLink WHERE portfolioId =? AND  valueStockId = ?)";
                try{
                    PreparedStatement statementGrowthStock = connection.prepareStatement(queryGrowthStock);
                    statementGrowthStock.setInt(1, portfolioId);
                    statementGrowthStock.setInt(2, stock.getId());

                    statementGrowthStock.executeUpdate();


                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                String queryValueStock = "DELETE FROM PortfolioValueStockLink WHERE portfolioId =? AND  growthStockId = ?)";
                try{
                    PreparedStatement statementValueStock = connection.prepareStatement(queryValueStock);
                    statementValueStock.setInt(1, portfolioId);
                    statementValueStock.setInt(2, stock.getId());

                    statementValueStock.executeUpdate();


                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
