package org.example.controller;

import org.example.model.Company;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.utils.factory.CompanyRepoFactory;

import java.util.List;

public class CompanyController {
    private static IRepository<Company> companyIRepository;

    private static RepoTypes repoType;
    private static CompanyController instance;
    private int companyIdCounter;

    private CompanyController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided");
        }
        companyIRepository = CompanyRepoFactory.createIRepository(repoType);
        this.companyIdCounter = companyIRepository.getObjects().size();
    }
    public static CompanyController getInstance() {
        if(instance == null) {
            instance = new CompanyController();
        }
        return instance;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

    public boolean addCompany(String name, long capitalization, long numberShares) {
        if (!this.searchCompanyBool(name)) {
            companyIRepository.save(new Company(companyIdCounter++, name, capitalization, numberShares));
            return true;
        }
        return false;
    }

    public boolean removeCompany(String name) {
        Company search = this.searchCompany(name);
        if (search != null) {
            companyIRepository.delete(search);
            return true;
        } else {
            return false;
        }
    }

    public List<Company> getAll() {
        return companyIRepository.getObjects();
    }

    public boolean searchCompanyBool(String name) {
        List<Company> companies = companyIRepository.getObjects();
        for (Company company : companies) {
            if (company.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Company searchCompany(String name) {
        List<Company> companies = companyIRepository.getObjects();
        for (Company company : companies) {
            System.out.println(company.getName());
            if (company.getName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    public boolean updateCompany(String companyName, String action, long number) {
//        Company search = this.searchCompany(companyName);
//        if (search != null) {
//            companyIRepository.update(search, action, number);
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}