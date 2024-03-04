package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "markets")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    private String location;

    public Market() {

    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public Market(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

