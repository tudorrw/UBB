package com.unternehmensplattform.backend.services.implementations;

import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.DTOs.UserDetailsDTO;
import com.unternehmensplattform.backend.entities.DTOs.UserWithWorkingDaysDetailsDTO;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.entities.WorkingDay;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.enums.VacationReqStatus;
import com.unternehmensplattform.backend.handler.*;
import com.unternehmensplattform.backend.entities.DTOs.WorkingDaysDTO;
import com.unternehmensplattform.backend.repositories.ContractRepository;
import com.unternehmensplattform.backend.repositories.UserRepository;
import com.unternehmensplattform.backend.repositories.VacationReqRepository;
import com.unternehmensplattform.backend.repositories.WorkingDaysRepository;
import com.unternehmensplattform.backend.services.interfaces.WorkingDaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Objects;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WorkingDaysServiceImpl implements WorkingDaysService {
    private final WorkingDaysRepository workingDaysRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteWorkingDay(Integer requestId) {
        WorkingDay workingDay = workingDaysRepository .findById(requestId).orElse(null);
        if (workingDay == null) {
            throw new VacationRequestNotFoundException("Working day with id " + requestId + " not found.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (!workingDay.getEmployee().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You can only edit your own activity reports.");
        }
        if (currentUser.getRole() == UserRole.Employee || currentUser.getRole()==UserRole.Administrator) {
            Contract contract = currentUser.getContract();
            if (contract != null) {
                if (Objects.equals(workingDay.getEmployee().getId(), currentUser.getId())) {
//
                    workingDaysRepository.deleteById(requestId);
                } else {
                    throw new IllegalArgumentException("working day does not belong to the employee who made the request");
                }
            } else {
                throw new RuntimeException("Employee has no contract");
            }
        } else {
            throw new RuntimeException("User is not an employee or an admin");
        }
    }
    private final ContractRepository contractRepository;
    private final VacationReqRepository vacationRequestRepository;

    @Override
    public void createActivityReport(WorkingDaysDTO dto, User loggedInUser) {
        validateContractExists(loggedInUser);
        validateStartAndEndDateConsistency(dto);
        validateDateConditionsForCreate(dto, loggedInUser);

        WorkingDay newWorkingDay = WorkingDay.builder()
                .employee(loggedInUser)
                .date(dto.getDate())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .build();

        workingDaysRepository.save(newWorkingDay);
    }

    @Override
    public void modifyActivityReport(WorkingDaysDTO dto, User loggedInUser) {
        WorkingDay workingDay = workingDaysRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("No activity report found for the specified id."));
        if (!workingDay.getEmployee().getId().equals(loggedInUser.getId())) {
            throw new IllegalArgumentException("You can only edit your own activity reports.");
        }
        validateStartAndEndDateConsistency(dto);
        validateDateConditionsForEdit(dto, loggedInUser);


        workingDay.setStartDate(dto.getStartDate());
        workingDay.setEndDate(dto.getEndDate());
        workingDay.setDescription(dto.getDescription());
        workingDay.setDate(dto.getDate());
        workingDaysRepository.save(workingDay);
    }

    @Override
    public List<WorkingDaysDTO> getAllActivityReports(User loggednInUser) {
        return workingDaysRepository.findAllByEmployee(loggednInUser)
                .stream()
                .map(this::convertWorkingDaysToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkingDaysDTO getActivityReportByDate(User loggedInUser, LocalDate date) {
        WorkingDay workingDay = workingDaysRepository.findWorkingDayByEmployeeAndDate(loggedInUser, date)
                .orElseThrow(() -> new IllegalArgumentException("No activity report found for the specified date."));
        return convertWorkingDaysToDto(workingDay);
    }

    private void validateDateConditionsForCreate(WorkingDaysDTO dto, User loggedInUser) {
        if(dto.getDate().isBefore(loggedInUser.getContract().getSigningDate())) {
            throw new WorkingDayBeforeSigningDate("Activity report should be after signing date.");
        }

        boolean overlaps = workingDaysRepository.findAllByEmployeeAndDate(loggedInUser, dto.getDate()).stream()
                .anyMatch(existingReport ->
                        !(dto.getStartDate().isAfter(existingReport.getEndDate()) ||
                                dto.getEndDate().isBefore(existingReport.getStartDate()) ||
                                (dto.getEndDate().equals(existingReport.getStartDate()) || dto.getStartDate().equals(existingReport.getEndDate())) // Allow exact match for adjacent events
                ));

        if (overlaps) {
            throw new WorkingDaysOverlapException("The time interval overlaps with an existing activity report.");
        }

        hasVacationToday(dto, loggedInUser);
    }

    private void validateDateConditionsForEdit(WorkingDaysDTO dto, User loggedInUser) {
        boolean overlaps = workingDaysRepository.findAllByEmployeeAndDate(loggedInUser, dto.getDate()).stream()
                .anyMatch(existingReport ->
                        !(dto.getStartDate().isAfter(existingReport.getEndDate()) ||  // New start time must be after existing end time
                                dto.getEndDate().isBefore(existingReport.getStartDate()) ||  // New end time must be before existing start time
                                (dto.getEndDate().equals(existingReport.getStartDate()) || dto.getStartDate().equals(existingReport.getEndDate())) || // Allow exact match for adjacent events
                                existingReport.getId().equals(dto.getId())));  // Exclude current report

        if (overlaps) {
            throw new WorkingDaysOverlapException("The time interval overlaps with an existing activity report.");
        }

        hasVacationToday(dto, loggedInUser);
    }



    private void hasVacationToday(WorkingDaysDTO workingDaysDTO, User loggedInUser) {
        if (vacationRequestRepository.findApprovedVacationRequestsByEmployeeId(loggedInUser.getId(), VacationReqStatus.Approved).stream()
                .anyMatch(vacationReq -> (workingDaysDTO.getDate().isBefore(vacationReq.getEndDate()) && workingDaysDTO.getDate().isAfter(vacationReq.getStartDate()))
                        || workingDaysDTO.getDate().isEqual(vacationReq.getStartDate()) || workingDaysDTO.getDate().isEqual(vacationReq.getEndDate())))
            throw new WDOverlapWithVDException("A vacation request overlaps with the provided date.");
    }

    private void validateContractExists(User loggedInUser) {
        if (!contractRepository.findByUser(loggedInUser).isPresent()) {
            throw new IllegalArgumentException("No contract found for the user.");
        }
    }

    private WorkingDaysDTO convertWorkingDaysToDto(WorkingDay workingDay) {
        Duration duration = Duration.between(workingDay.getStartDate(), workingDay.getEndDate());
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        double effectiveHours = hours + (minutes / 60.0);

        return WorkingDaysDTO.builder()
                .id(workingDay.getId())
                .date(workingDay.getDate())
                .startDate(workingDay.getStartDate())
                .endDate(workingDay.getEndDate())
                .description(workingDay.getDescription())
                .effectiveTime(String.format("%d hours %d minutes", hours, minutes))
                .effectiveHours(effectiveHours)
                .build();
    }

    private void validateStartAndEndDateConsistency(WorkingDaysDTO dto) {
        Instant startDateInstant = dto.getStartDate();
        Instant endDateInstant = dto.getEndDate();

        LocalDate startDate = LocalDate.ofInstant(startDateInstant, ZoneOffset.UTC);
        LocalDate endDate = LocalDate.ofInstant(endDateInstant, ZoneOffset.UTC);

        if (!dto.getDate().equals(startDate) || !dto.getDate().equals(endDate)) {
            throw new WorkingDaySameDatesException("Date, start date, and end date must all be on the same day.");
        }
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new ArrivalBeforeLeaveException("Start date must be before end date.");
        }
    }

    @Override
    public List<UserWithWorkingDaysDetailsDTO> getEmployeesWithWorkingDays() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (currentUser.getRole() != UserRole.Administrator) {
            throw new RuntimeException("User is not an administrator");
        }

        if (currentUser.getContract() == null) {
            throw new RuntimeException("Admin has no contract");
        }

        List<User> employees = userRepository.findUsersByCompany(UserRole.Employee, currentUser.getContract().getCompany());

        return employees.stream().map(userWithWD -> UserWithWorkingDaysDetailsDTO.builder()
                        .userDetailsDTO(UserDetailsDTO.builder()
                                .id(userWithWD.getId())
                                .firstName(userWithWD.getFirstName())
                                .lastName(userWithWD.getLastName())
                                .email(userWithWD.getEmail())
                                .build())
                        .workingDays(workingDaysRepository.findAllByEmployee(userWithWD).stream()
                                .map(this::convertWorkingDaysToDto)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
