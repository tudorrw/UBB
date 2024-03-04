package org.example.controller;


import org.example.model.Transaction;
import org.example.repo.IRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionController {
    private IRepository<Transaction> repository;

    public TransactionController(IRepository<Transaction> repository) {
        this.repository = repository;
    }

    public boolean addTransaction(int id, int userId, int stockId, LocalDateTime date){
        if(!this.searchTransactionBool(id)) {
            repository.save(new Transaction(id, userId,stockId,date));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeTransaction(int id){
        Transaction search = this.searchTransaction(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Transaction> getAll(){
        return repository.getObjects();
    }

    public boolean searchTransactionBool(int id){
        List<Transaction> transactions = repository.getObjects();
        for (Transaction transaction: transactions) {
            if(transaction.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Transaction searchTransaction(int id){
        List<Transaction> transactions = repository.getObjects();
        for (Transaction transaction: transactions) {
            if(transaction.getId() == id){
                return transaction;
            }
        }
        return null;
    }
}
