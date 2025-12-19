package com.unternehmensplattform.backend.entities.DTOs;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@Data
public class WorkingDaysDTO {
    private Integer id;

    private LocalDate date;

    private Instant startDate;

    private Instant endDate;

    @Size(max = 256, message = "Description must be between 0 and 256 characters")
    private String description;
    private String effectiveTime;
    private Double effectiveHours;

}
