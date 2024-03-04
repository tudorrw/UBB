package map.project.stockApp.service;

import map.project.stockApp.model.Company;
import map.project.stockApp.repo.RepoTypes;
import map.project.stockApp.repo.springRepo.CompanyRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepositorySpring repository;

    private static CompanyService instance;
    private CompanyService() {

    }
    public static CompanyService getInstance() {
        if(instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }


    public List<Company> getAll() {
        return repository.findAll();
    }


    public Company searchCompany(String name) {
        return repository.findByName(name);
    }


    public void addCompanyObject(Company company) {
        if(!repository.existsById(company.getId()) && !repository.existsCompaniesByName(company.getName())) {
            repository.save(company);
        }
    }


    public void deleteCompany(int id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
    public Optional<Company> getCompanyByName(String name) {
        return repository.findCompanyByName(name);
    }


    public void updateCompany(int id, String newName, long newCapitalization, long newNumberShares, long newNumberSharesOutstanding) {
        Company company = repository.findById(id).orElse(null);
        if(company != null) {
            company.setName(newName);
            company.setCapitalization(newCapitalization);
            company.setNumber_shares(newNumberShares);
            company.setNumber_shares_outstanding(newNumberSharesOutstanding);
            repository.save(company);
        }
    }
}