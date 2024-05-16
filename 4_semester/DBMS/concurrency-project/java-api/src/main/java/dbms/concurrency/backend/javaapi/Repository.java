package dbms.concurrency.backend.javaapi;

import dbms.concurrency.backend.javaapi.config.DBConnection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@AllArgsConstructor
@NoArgsConstructor
public class Repository {

    private Connection connection = DBConnection.getConnection();
    public Optional<Band> findById(Integer id) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM bands_concurrency WHERE id = ?");
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            // Process the result (print the nationality)
            if (resultSet.next()) {
                Integer bandId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int forming_year = resultSet.getInt("forming_year");
                Band band = new Band(bandId, name, country, forming_year);
                return Optional.of(band);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
