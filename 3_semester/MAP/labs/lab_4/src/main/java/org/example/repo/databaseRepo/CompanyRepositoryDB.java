package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Company;
import org.example.model.Market;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepositoryDB implements IRepository<Company> {
    private Connection connection = DBConnection.getConnection();

    private static CompanyRepositoryDB instance;



    private List<Company> cache;

    public static CompanyRepositoryDB getInstance() {
        if(instance == null) {
            instance = new CompanyRepositoryDB();
        }
        return instance;
    }

    public CompanyRepositoryDB() {
        this.cache = importCache();
    }
    public Company searchById(int id) {
        List<Company> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Companies WHERE companyId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            while(results.next())
            {
                int id_repository = results.getInt("companyId");
                String name = results.getString("name");
                long capitalization = results.getLong("capitalization");
                long numberShares = results.getLong("numberShares");
                result.add(new Company(id_repository,name,capitalization,numberShares));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result.isEmpty()) {
            return null;
        }
        return result.getFirst();
    }

    private List<Company> importCache(){
        List<Company> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Companies";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("companyId");
                String name = results.getString("name");
                long capitalization = results.getLong("capitalization");
                long numberShares = results.getLong("numberShares");
                result.add(new Company(id,name,capitalization,numberShares));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public Company searchIdCache(int id) {
        for(Company company: this.cache){
            if (company.getId() == id){
                return company;
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

    @Override
    public List<Company> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Company company) {
        int id = company.getId();
        String name = company.getName();
        long capitalization = company.getCapitalization();
        long numberShares =  company.getNumberShares();
        String query = "INSERT INTO Companies (companyId, name, capitalization, numberShares) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setLong(3, capitalization);
            statement.setLong(4, numberShares);
            statement.executeUpdate();

            this.cache.add(company);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Company entity) {
        int id = entity.getId();
        String name = entity.getName();
        long capitalization = entity.getCapitalization();
        long numberShares =  entity.getNumberShares();

        Company old_company = searchIdCache(id);

        if(old_company !=null) {
            try {
                String query = "UPDATE Companies SET name = ?, capitalization = ?, numberShares = ?  WHERE companyId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(4, id);
                statement.setString(1, name);
                statement.setLong(2, capitalization);
                statement.setLong(3, numberShares);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_company.setName(name);
            old_company.setCapitalization(capitalization);
            old_company.setNumberShares(numberShares);
        }

    }

    @Override
    public void delete(Company object) {
        int id = object.getId();
        String query = "DELETE FROM Companies WHERE companyId = ?";

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

}
