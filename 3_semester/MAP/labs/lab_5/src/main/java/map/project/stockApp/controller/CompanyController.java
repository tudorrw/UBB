package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.CompanyRequest;
import map.project.stockApp.model.Company;
import map.project.stockApp.model.Role;
import map.project.stockApp.service.CompanyService;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompanyController {
    private final CompanyService companyService;
    private final CredentialChecker credentialChecker;

    public CompanyController(CompanyService companyService, CredentialChecker credentialChecker) {
        this.companyService = companyService;
        this.credentialChecker = credentialChecker;
    }

    @GetMapping("/companies")
    @ResponseBody
    List<Company> seeCompanies() {
        return companyService.getAll();
    }

    @GetMapping("/companies/getByName/{name}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("name") String name) {
        return companyService.getCompanyByName(name)
                .map(equipmentItem -> ResponseEntity.ok().body(equipmentItem))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/companies/add")
    public String addCompany(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CompanyRequest companyRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            Company company = new Company(companyRequest.getId(), companyRequest.getName(), companyRequest.getCapitalization(), companyRequest.getNumber_shares(), companyRequest.getNumber_shares_outstanding());
            companyService.addCompanyObject(company);
        }
        return "redirect:/companies";
    }

    @PostMapping("/companies/addTest")
    public String addCompanyTest(@RequestBody CompanyRequest companyRequest) {
        Company company = new Company(companyRequest.getId(), companyRequest.getName(), companyRequest.getCapitalization(), companyRequest.getNumber_shares(), companyRequest.getNumber_shares_outstanding());
        companyService.addCompanyObject(company);
        return "redirect:/companies";
    }

    @DeleteMapping(path = "/companies/delete/{id}")
    public String deleteCompany(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            companyService.deleteCompany(id);
        }
        return "redirect:/companies";
    }

    @PutMapping("/companies/update/{id}")
    public ResponseEntity<String> updateCompany(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id, @RequestBody CompanyRequest companyRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            companyService.updateCompany(id, companyRequest.getName(), companyRequest.getCapitalization(), companyRequest.getNumber_shares(), companyRequest.getNumber_shares_outstanding());
            return ResponseEntity.ok("Succes");
        }

        return ResponseEntity.badRequest().build();
    }
}
