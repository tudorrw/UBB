package com.unternehmensplattform.backend.entities.DTOs;

import com.unternehmensplattform.backend.entities.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyDetailsDTO {
    @Valid
    @NotNull
    private CompanyDTO companyDTO;

    @Valid
    private List<UserDetailsDTO> admins;
}
