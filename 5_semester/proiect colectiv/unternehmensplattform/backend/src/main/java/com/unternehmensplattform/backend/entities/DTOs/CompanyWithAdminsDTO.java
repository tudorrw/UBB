package com.unternehmensplattform.backend.entities.DTOs;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyWithAdminsDTO {

    @Valid
    @NotNull
    private CompanyDTO companyDTO;

    @Valid
    private List<RegistrationRequest> adminRegistrations; // List of administrators

}
