package org.example.controller;

import org.example.model.Company;
import org.example.repo.IRepository;

import java.util.List;

public class CompanyController {
    private IRepository<Company> repository;

    public CompanyController(IRepository<Company> repository) {
        this.repository = repository;
    }

    public boolean addCompany(int id, String name, int capitalization){
        if(!this.searchCompanyBool(id)) {
            repository.save(new Company(id, name, capitalization));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeCompany(int id){
        Company search = this.searchCompany(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Company> getAll(){
        return repository.getObjects();
    }

    public boolean searchCompanyBool(int id){
        List<Company> companies = repository.getObjects();
        for (Company company: companies) {
            if(company.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Company searchCompany(int id){
        List<Company> companies = repository.getObjects();
        for (Company company: companies) {
            if(company.getId() == id){
                return company;
            }
        }
        return null;
    }

}
