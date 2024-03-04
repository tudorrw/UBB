package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private LocalDateTime date;
    private int quantity;
    private String type;

    public Transaction(User user, Stock stock, LocalDateTime date, int quantity, String type) {
        this.user = user;
        this.stock = stock;
        this.date = date;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", stock=" + stock.getName() +
                ", date=" + date +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                '}';
    }
}
