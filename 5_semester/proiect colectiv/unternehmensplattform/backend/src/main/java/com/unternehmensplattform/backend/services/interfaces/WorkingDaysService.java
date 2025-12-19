package com.unternehmensplattform.backend.services.interfaces;

import com.unternehmensplattform.backend.entities.DTOs.UserWithWorkingDaysDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.WorkingDaysDTO;
import com.unternehmensplattform.backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface WorkingDaysService {

    void deleteWorkingDay(Integer requestId);

    void createActivityReport(WorkingDaysDTO dto, User loggedInUser);

    void modifyActivityReport(WorkingDaysDTO dto, User loggedInUser);

    List<WorkingDaysDTO> getAllActivityReports(User user);

    WorkingDaysDTO getActivityReportByDate(User user, LocalDate date);

    List<UserWithWorkingDaysDetailsDTO> getEmployeesWithWorkingDays();
}