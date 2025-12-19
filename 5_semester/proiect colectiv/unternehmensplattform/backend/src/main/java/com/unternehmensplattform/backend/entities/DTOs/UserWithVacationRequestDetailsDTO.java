package com.unternehmensplattform.backend.entities.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWithVacationRequestDetailsDTO {
    @Valid
    @NotNull
    private UserDetailsDTO userDetailsDTO;
    @Valid
    private List<VacationRequestDetailsDTO> vacationRequests;

}
