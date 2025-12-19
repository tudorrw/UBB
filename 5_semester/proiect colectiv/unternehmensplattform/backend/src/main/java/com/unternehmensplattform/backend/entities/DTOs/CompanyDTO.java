package com.unternehmensplattform.backend.entities.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
public class CompanyDTO {
    private Integer companyId;
    private String name;
    private String telefonNumber;
    private String address;


}
