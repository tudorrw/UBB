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
import java.util.List;
import java.util.Optional;

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

    public List<Band> findAll(){
        return bandRepository.findAll();
    }
    public Optional<Band> getBandById(Integer id) {
        return repository.findById(id);
    }
    public void callDirtyRead(Integer bandId){
        String pythonUrl = "http://localhost:8000/simulate-update";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"id\": " + bandId + "}", headers);
        restTemplate.postForEntity(pythonUrl, requestEntity, String.class);
    }

    public Band dirty_read(Integer bandId) throws InterruptedException {
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
            throw new RuntimeException(e);
        }
        return null;
    }
}
