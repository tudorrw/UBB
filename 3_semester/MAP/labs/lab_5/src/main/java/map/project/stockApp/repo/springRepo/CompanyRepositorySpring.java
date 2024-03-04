package map.project.stockApp.repo.springRepo;


import map.project.stockApp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepositorySpring extends JpaRepository<Company, Integer> {
    Optional<Company> findCompanyByName(String name);

    Company findByName(String name);

    Boolean existsCompaniesByName(String name);


}
