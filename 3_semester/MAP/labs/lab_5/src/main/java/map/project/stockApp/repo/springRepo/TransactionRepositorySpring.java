package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.Transaction;
import map.project.stockApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepositorySpring extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUser(User user);

}
