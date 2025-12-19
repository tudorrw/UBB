package com.unternehmensplattform.backend.services.interfaces;

import com.unternehmensplattform.backend.entities.DTOs.CompanyDTO;
import com.unternehmensplattform.backend.entities.DTOs.CompanyDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.CompanyWithAdminsDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {
    List<CompanyDetailsDTO> getAllCompanies();

    void createCompanyWithAdmins(@Valid CompanyWithAdminsDTO companyWithAdminDTO);

    void assignAdminToCompany(Integer companyId, Integer adminId);
}
