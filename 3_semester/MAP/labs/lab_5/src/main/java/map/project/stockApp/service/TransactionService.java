package map.project.stockApp.service;


import map.project.stockApp.model.Stock;
import map.project.stockApp.model.Transaction;
import map.project.stockApp.model.User;
import map.project.stockApp.repo.springRepo.TransactionRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepositorySpring repository;

    public TransactionService() {
    }


    public boolean addTransaction(User userId, Stock stockId, LocalDateTime date, int quantity, String type) {
        repository.save(new Transaction(userId, stockId, date, quantity, type));
        return true;
    }


    public List<Transaction> getAll() {
        return repository.findAll();
    }


    public Transaction searchTransaction(int id) {
        Transaction transaction = repository.findById(id).orElse(null);
        if (transaction.getId() == id) {
            return transaction;
        }

        return null;
    }

    public boolean deleteTransaction(int id) {
        Transaction search = this.searchTransaction(id);
        if (search != null) {
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }

    public List<Transaction> getByUser(User user) {
        return repository.findAllByUser(user);
    }

}
