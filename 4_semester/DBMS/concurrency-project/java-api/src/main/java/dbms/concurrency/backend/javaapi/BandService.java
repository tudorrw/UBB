package dbms.concurrency.backend.javaapi;


import dbms.concurrency.backend.javaapi.config.DBConnection;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.util.*;

@Service
public class BandService {
    @Autowired
    private BandRepository bandRepository;

    private final Repository repository;

    @Autowired
    private RestTemplate restTemplate;


    public BandService( ) {
        this.repository = new Repository();
    }

    public List<Band> getAllBands(){
        return repository.findAll();
    }
    public Optional<Band> getBandById(Integer id) {
        return repository.findById(id);
    }
    public void updateBandNameById(Integer id, String newName) { repository.updateNameById(id, newName);}

    public void dirtyReadPython(Integer bandId){
        String pythonUrl = "http://localhost:8000/dirty-read-python";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"id\": " + bandId + "}", headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }
    public void lostUpdatePython(Integer bandId){
        String pythonUrl = "http://localhost:8000/lost-update-python";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"id\": " + bandId + "}", headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }
    public void unrepetableReadPython(Integer bandId){
        String pythonUrl = "http://localhost:8000/unrepetable-reads-python";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"id\": " + bandId + "}", headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }

    public void dirtyWritePython(Integer bandId) {
        String pythonUrl = "http://localhost:8000/dirty-write-python";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"id\": " + bandId + "}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }

    public void phantomReadPython() {
        String pythonUrl = "http://localhost:8000/phantom-read-python";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }



    public Band dirtyRead(Integer bandId) throws InterruptedException {
        Thread.sleep(2500); // 2,5 seconds delay
        try (Connection connection = DBConnection.getConnection()) {
            // Read the nationality of the artist without waiting for updates (dirty read)
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM bands_concurrency WHERE id = ?");
            selectStatement.setInt(1, bandId);
            ResultSet resultSet = selectStatement.executeQuery();
            // Process the result (print the nationality)
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int forming_year = resultSet.getInt("forming_year");
                return new Band(id, name, country, forming_year);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void lostUpdate (Integer bandId) throws InterruptedException {
//        Thread.sleep(2000);
//        System.out.println("aaa");
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            Thread.sleep(3000);

            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE bands_concurrency SET name = ? WHERE id = ?");
            updateStatement.setString(1, "UpdatedJava");
            updateStatement.setInt(2, bandId);
            updateStatement.executeUpdate();
            System.out.println("commit");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> unrepeatableRead(Integer bandId) throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // First read
            PreparedStatement selectStatement1 = connection.prepareStatement(
                    "SELECT * FROM bands_concurrency WHERE id = ?");
            selectStatement1.setInt(1, bandId);
            ResultSet resultSet1 = selectStatement1.executeQuery();
            Band band = null;
            if(resultSet1.next()) {
                Integer id = resultSet1.getInt("id");
                String name = resultSet1.getString("name");
                String country = resultSet1.getString("country");
                int forming_year = resultSet1.getInt("forming_year");
                band = new Band(id, name, country, forming_year);
            }
            result.put("Before: ", band.toString());
            System.out.println(band.toString());
            // Wait to allow the concurrent update
            Thread.sleep(4000);

            // Second read
            Band band2 = null;
            PreparedStatement selectStatement2 = connection.prepareStatement(
                    "SELECT * FROM bands_concurrency WHERE id = ?");
            selectStatement2.setInt(1, bandId);
            ResultSet resultSet2 = selectStatement2.executeQuery();
            if(resultSet2.next()) {
                Integer id = resultSet2.getInt("id");
                String name = resultSet2.getString("name");
                String country = resultSet2.getString("country");
                int forming_year = resultSet2.getInt("forming_year");
                band2 = new Band(id, name, country, forming_year);
            }

            System.out.println(band2.toString());
            result.put("After: ", band2.toString());

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void dirtyWrite (Integer bandId) throws InterruptedException {
        Thread.sleep(2500); // Ensure Python update happens first
        try (Connection connection = DBConnection.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            connection.setAutoCommit(false);
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE bands_concurrency SET name = ? WHERE id = ?");
            updateStatement.setString(1, "DirtyWriteJava");
            updateStatement.setInt(2, bandId);
            updateStatement.executeUpdate();
            System.out.println("Java update executed");
            Thread.sleep(2000); // Simulate delay before commit
            System.out.println("java commit");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void phantomRead() throws InterruptedException {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);

            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM bands_concurrency");
            ResultSet resultSet = selectStatement.executeQuery();

            List<Band> beforeBands = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int forming_year = resultSet.getInt("forming_year");
                beforeBands.add(new Band(id, name, country, forming_year));
            }
            System.out.println("Before Transaction: " + beforeBands.size());

            Thread.sleep(3000);  // Wait for Python to insert

            resultSet = selectStatement.executeQuery();
            List<Band> afterBands = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int forming_year = resultSet.getInt("forming_year");
                afterBands.add(new Band(id, name, country, forming_year));
            }
            System.out.println("After Transaction: " + afterBands.size());

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
