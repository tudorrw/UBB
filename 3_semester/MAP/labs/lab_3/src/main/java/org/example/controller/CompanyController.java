package org.example.controller;

import org.example.model.Company;
import org.example.repo.CompanyRepository;
import org.example.repo.IRepository;

import java.util.List;
import java.util.Objects;

public class CompanyController {
//    private IRepository<Company> companyIRepository;
    private CompanyRepository companyIRepository;
    private int companyIdCounter;

    public CompanyController(CompanyRepository companyIRepository) {

        this.companyIRepository = companyIRepository;
        this.companyIdCounter = companyIRepository.getObjects().size();
    }

    public boolean addCompany(String name, long capitalization, long numberShares){
        if(!this.searchCompanyBool(name)) {
            companyIRepository.save(new Company(companyIdCounter++, name, capitalization, numberShares));
            return true;
        }
        return false;
    }

    public boolean removeCompany(String name){
        Company search = this.searchCompany(name);
        if(search != null) {
            companyIRepository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Company> getAll(){
        return companyIRepository.getObjects();
    }

    public boolean searchCompanyBool(String name){
        List<Company> companies = companyIRepository.getObjects();
        for (Company company: companies) {
            if(company.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Company searchCompany(String name){
        List<Company> companies = companyIRepository.getObjects();
        for (Company company: companies) {
            if(company.getName().equals(name)){
                return company;
            }
        }
        return null;
    }

    public boolean updateCompany(String companyName, String action, long number) {
        Company search = this.searchCompany(companyName);
        if(search != null) {
            companyIRepository.update(search,action,number);
            return true;
        }
        else{
            return false;
        }
    }
}
