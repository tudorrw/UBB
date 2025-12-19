package com.unternehmensplattform.backend.services.interfaces;

import com.unternehmensplattform.backend.entities.DTOs.UserDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.VacationRequestDTO;
import com.unternehmensplattform.backend.entities.DTOs.VacationRequestDetailsDTO;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.entities.VacationRequest;

import java.util.List;

import com.unternehmensplattform.backend.enums.VacationReqStatus;


import com.unternehmensplattform.backend.entities.DTOs.UserWithVacationRequestDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.VacationRequestDetailsDTO;

import java.util.List;

public interface VacationReqService {
    void updateRequestStatus(Integer requestId, VacationReqStatus status);
    List<VacationRequestDetailsDTO> getAllPendingVacationRequests();

    List<UserWithVacationRequestDetailsDTO> getAllEmployeesWithVacationRequests();

    List<VacationRequestDetailsDTO> getVacationRequestsByEmployee();

    List<VacationRequestDetailsDTO> getApprovedVacationRequestsByEmployee();

    void deleteVacationRequest(Integer requestId);

    void createVacationRequest(VacationRequestDTO vacationRequestDTO, User employee);

    List<UserDetailsDTO> getAvailableAdministrators(User loggedInUser);

    VacationRequest getVacationRequestById(Integer requestId);
}