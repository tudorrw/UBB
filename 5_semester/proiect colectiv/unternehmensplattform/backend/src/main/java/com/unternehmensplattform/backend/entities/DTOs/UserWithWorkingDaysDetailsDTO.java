package com.unternehmensplattform.backend.entities.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserWithWorkingDaysDetailsDTO {
    @Valid
    @NotNull
    private UserDetailsDTO userDetailsDTO;
    @Valid
    private List<WorkingDaysDTO> workingDays;
}