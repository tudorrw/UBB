package org.example.repo.inMemoryRepo;

import org.example.model.Company;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyRepositoryIM implements IRepository<Company> {
    private List<Company> companies;
    private static CompanyRepositoryIM instance;

    void insert_values() {
        Company apple = new Company(1, "Apple Inc.", 2_000_000_000, 10_000);
        Company boeing = new Company(2, "Boeing", 1_200_000_000, 12_000);
        Company intel = new Company(3, "Intel", 250_000_000, 250_000);
        companies.add(apple);
        companies.add(boeing);
        companies.add(intel);
    }
    private CompanyRepositoryIM() {

        this.companies = new ArrayList<>();
        insert_values();
    }

    @Override
    public List<Company> getObjects() {
        return companies;
    }

    @Override
    public void save(Company entity) {
        companies.add(entity);
    }

    @Override
    public void update(Company entity) {

    }


    public void update(Company entity, String action, long number) {
        if(Objects.equals(action, "capitalization")){
            entity.setCapitalization(number);
        }
        if(Objects.equals(action, "numberShares")){
            entity.setNumberShares(number);
        }

    }

    @Override
    public void delete(Company object) {
        companies.remove(object);
    }


    public static CompanyRepositoryIM getInstance() {
        if (instance == null) {
            instance = new CompanyRepositoryIM();
        }
        return instance;
    }

}
