package org.example.repo.inMemoryRepo;

import org.example.model.Transaction;
import org.example.repo.IRepository;

import java.util.ArrayList;

public class TransactionRepositoryIM implements IRepository<Transaction> {
    private ArrayList<Transaction> transactions;
    private static TransactionRepositoryIM instance;


    private TransactionRepositoryIM() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public ArrayList<Transaction> getObjects() {
        return transactions;
    }

    @Override
    public void save(Transaction entity) {
        transactions.add(entity);
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Transaction object) {
        transactions.remove(object);
    }


    public static TransactionRepositoryIM getInstance() {
        if (instance == null) {
            instance = new TransactionRepositoryIM();
        }
        return instance;
    }
}
