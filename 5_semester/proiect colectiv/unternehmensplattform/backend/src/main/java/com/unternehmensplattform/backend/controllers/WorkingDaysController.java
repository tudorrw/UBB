package com.unternehmensplattform.backend.controllers;

import com.unternehmensplattform.backend.entities.DTOs.UserWithWorkingDaysDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.WorkingDaysDTO;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.services.interfaces.WorkingDaysService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("working-days")
@RequiredArgsConstructor
public class WorkingDaysController {
    private final WorkingDaysService workingDaysService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createWorkingDay(@RequestBody @Valid WorkingDaysDTO dto) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            workingDaysService.createActivityReport(dto, loggedInUser);
            return ResponseEntity.accepted().build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyWorkingDay(@RequestBody @Valid WorkingDaysDTO dto) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        workingDaysService.modifyActivityReport(dto, loggedInUser);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/get-all-working-days-by-employee")
    public ResponseEntity<List<WorkingDaysDTO>> getAllActivityReports() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<WorkingDaysDTO> activityReports = workingDaysService.getAllActivityReports(loggedInUser);
        return ResponseEntity.ok(activityReports);
    }


    @GetMapping("/today")
    public ResponseEntity<WorkingDaysDTO> getTodayActivityReport() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingDaysDTO activityReport = workingDaysService.getActivityReportByDate(loggedInUser, LocalDate.now());
        return ResponseEntity.ok(activityReport);
    }



    @PostMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteWorkingDay(@PathVariable Integer requestId) {
        workingDaysService.deleteWorkingDay(requestId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/get-employees-with-working-days")
    public ResponseEntity<List<UserWithWorkingDaysDetailsDTO>> getEmployeesWithWorkingDays() {
        List<UserWithWorkingDaysDetailsDTO> activityReports = workingDaysService.getEmployeesWithWorkingDays();
        return ResponseEntity.ok(activityReports);
    }
}

