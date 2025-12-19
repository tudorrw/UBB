package com.unternehmensplattform.backend.entities.DTOs;

import com.unternehmensplattform.backend.entities.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VacationRequestDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer assignedAdministratorId;

}
