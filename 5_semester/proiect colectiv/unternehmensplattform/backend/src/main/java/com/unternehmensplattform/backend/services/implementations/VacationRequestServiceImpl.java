package com.unternehmensplattform.backend.services.implementations;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import com.unternehmensplattform.backend.entities.*;
import com.unternehmensplattform.backend.entities.DTOs.*;
import com.unternehmensplattform.backend.enums.*;
import com.unternehmensplattform.backend.handler.*;
import com.unternehmensplattform.backend.repositories.*;
import com.unternehmensplattform.backend.services.interfaces.VacationReqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacationRequestServiceImpl implements VacationReqService {

    private final VacationReqRepository vacationRequestRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final WorkingDaysRepository workingDaysRepository;


    @Override
    public void updateRequestStatus(Integer requestId, VacationReqStatus status) {
        VacationRequest request = vacationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Vacation request not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentAdmin = (User) authentication.getPrincipal();

        if (request.getAdministrator().getId() != currentAdmin.getId()) {
            throw new RuntimeException("You are not authorized to modify this request");
        }

        if (status == VacationReqStatus.Approved) {
            List<WorkingDay> workingDays = workingDaysRepository.findAllByEmployeeAndDateBetween(
                    request.getEmployee(),
                    request.getStartDate(),
                    request.getEndDate()
            );

            if (!workingDays.isEmpty()) {
                request.setStatus(VacationReqStatus.Rejected);
                vacationRequestRepository.save(request);
                recalculateVacationDays(request);
                throw new VROverlapsWithWDException("Ceva nu-i bine");
            }
        }

        request.setStatus(status);
        vacationRequestRepository.save(request);

        if (status == VacationReqStatus.Rejected) {
            recalculateVacationDays(request);
        }
    }

    private void recalculateVacationDays(VacationRequest request) {
        int vacationDays = (int) calculateWeekdays(request.getStartDate(), request.getEndDate());
        Contract employeeContract = request.getEmployee().getContract();
        employeeContract.setActualYearVacationDays(employeeContract.getActualYearVacationDays() + vacationDays);
        contractRepository.save(employeeContract);
    }


    @Override
    public List<VacationRequestDetailsDTO> getAllPendingVacationRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (currentUser.getRole() == UserRole.Administrator) {
            if (currentUser.getContract() != null) {
                List<VacationRequest> vacationRequests = vacationRequestRepository.findByAdministratorIdAndStatus(currentUser.getId(), VacationReqStatus.New);
                return vacationRequests.stream()
                        .map(vacationRequest -> VacationRequestDetailsDTO.builder()
                                .id(vacationRequest.getId())
                                .employeeEmail(vacationRequest.getEmployee().getEmail())
                                .employeeId(vacationRequest.getEmployee().getId())
                                .requestedDate(vacationRequest.getRequestedDate())
                                .employeeFullName(vacationRequest.getEmployee().getFirstName() + " " + vacationRequest.getEmployee().getLastName())
                                .startDate(vacationRequest.getStartDate())
                                .endDate(vacationRequest.getEndDate())
                                .description(vacationRequest.getDescription())
                                .status(vacationRequest.getStatus())
                                .vacationDays((int)calculateWeekdays(vacationRequest.getStartDate(), vacationRequest.getEndDate()))
                                .build())
                        .collect(Collectors.toList());
            } else {
                throw new RuntimeException("Admin has no contract");
            }
        } else {
            throw new RuntimeException("User is not an administrator");
        }
    }

    @Override
    public List<UserWithVacationRequestDetailsDTO> getAllEmployeesWithVacationRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (currentUser.getRole() != UserRole.Administrator) {
            throw new RuntimeException("User is not an administrator");
        }

        if (currentUser.getContract() == null) {
            throw new RuntimeException("Admin has no contract");
        }

        // Fetch all employees linked to the administrator's company
        List<User> employees = userRepository.findUsersByCompany(UserRole.Employee, currentUser.getContract().getCompany());

        // Fetch vacation requests assigned to the administrator
        List<VacationRequest> vacationRequests = vacationRequestRepository
                .findByAdministratorIdAndStatusInOrderByStartDateDesc(
                        currentUser.getId(),
                        VacationReqStatus.Approved,
                        VacationReqStatus.Rejected
                );

        // Group vacation requests by employee
        Map<User, List<VacationRequest>> groupedRequests = vacationRequests.stream()
                .collect(Collectors.groupingBy(VacationRequest::getEmployee));

        // Map all employees to UserWithVacationRequestDetailsDTO
        return employees.stream()
                .map(employee -> {
                    List<VacationRequest> employeeRequests = groupedRequests.getOrDefault(employee, new ArrayList<>());

                    return UserWithVacationRequestDetailsDTO.builder()
                            .userDetailsDTO(UserDetailsDTO.builder()
                                    .id(employee.getId())
                                    .firstName(employee.getFirstName())
                                    .lastName(employee.getLastName())
                                    .email(employee.getEmail())
                                    .telefonNumber(employee.getTelefonNumber())
                                    .accountLocked(employee.isAccountLocked())
                                    .role(employee.getRole())
                                    .fullName(employee.getFirstName() + " " + employee.getLastName())
                                    .companyName(employee.getContract() != null ? employee.getContract().getCompany().getName() : null)
                                    .actualYearVacationDays(employee.getContract() != null ? employee.getContract().getActualYearVacationDays() : null)
                                    .build())
                            .vacationRequests(employeeRequests.stream()
                                    .map(vacationRequest -> VacationRequestDetailsDTO.builder()
                                            .id(vacationRequest.getId())
                                            .requestedDate(vacationRequest.getRequestedDate())
                                            .startDate(vacationRequest.getStartDate())
                                            .endDate(vacationRequest.getEndDate())
                                            .description(vacationRequest.getDescription())
                                            .status(vacationRequest.getStatus())
                                            .vacationDays((int) calculateWeekdays(
                                                    vacationRequest.getStartDate(),
                                                    vacationRequest.getEndDate()
                                            ))
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }


    public List<VacationRequest> getRequestsByAdmin(Integer administratorId) {
        return vacationRequestRepository.findByAdministratorId(administratorId);
    }

    @Override
    public void createVacationRequest(VacationRequestDTO vacationRequestDTO, User employee) {
        Contract contract = contractRepository.findByUser(employee)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("No contract found for user: %s", employee.getUsername())
                ));

        validateDates(vacationRequestDTO.getStartDate(), vacationRequestDTO.getEndDate(), employee);
        ensureNoOverlappingRequests(vacationRequestDTO.getStartDate(), vacationRequestDTO.getEndDate(), employee);
        hasWorkingDay(vacationRequestDTO, employee);


        User administrator = userRepository.findById(vacationRequestDTO.getAssignedAdministratorId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        if (administrator.getContract().getCompany().getId() != contract.getCompany().getId()) {
            throw new IllegalArgumentException("Admin does not have the same company as the employee");
        }

        long requestedWeekdays = calculateWeekdays(vacationRequestDTO.getStartDate(), vacationRequestDTO.getEndDate());


        if (requestedWeekdays > employee.getContract().getActualYearVacationDays()) {
            throw new InvalidVacationRequestException(
                    String.format("Requested days (%d) exceed available vacation days (%d) for the current year.",
                            requestedWeekdays, employee.getContract().getActualYearVacationDays())
            );
        }

        // Build the vacation request
        VacationRequest vacationRequest = buildVacationRequest(vacationRequestDTO, employee, administrator);

        contract.setActualYearVacationDays(contract.getActualYearVacationDays() - (int) requestedWeekdays);
        contractRepository.save(contract);

        vacationRequest = vacationRequestRepository.save(vacationRequest);

        String pdfPath = generatePdf(vacationRequest);

        vacationRequest.setPdfPath(pdfPath);
        vacationRequestRepository.save(vacationRequest);


    }

    private void hasWorkingDay(VacationRequestDTO vacationRequestDTO, User loggedInUser) {
        boolean hasWorkingDayOverlap = workingDaysRepository.findAllByEmployeeAndDateBetween(loggedInUser, vacationRequestDTO.getStartDate(), vacationRequestDTO.getEndDate())
                .stream()
                .anyMatch(workingDay ->
                        (vacationRequestDTO.getStartDate().isBefore(workingDay.getDate()) && vacationRequestDTO.getEndDate().isAfter(workingDay.getDate())) ||
                                vacationRequestDTO.getStartDate().isEqual(workingDay.getDate()) ||
                                vacationRequestDTO.getEndDate().isEqual(workingDay.getDate())
                );

        if (hasWorkingDayOverlap) {
            throw new VROverlapsWithWDException("An activity report overlaps with the requested vacation period.");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate, User employee) {

        if (startDate.isAfter(endDate)) {
            throw new VacationRequestValidationDatesException("Start date must be before or equal to the end date.");
        }

        if (startDate.isBefore(employee.getContract().getSigningDate())) {
            throw new VacationRequestValidationDatesException("Start date cannot be before the signing date.");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new VacationRequestValidationDatesException("Start date must be after today");
        }
    }

    private long calculateWeekdays(LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .filter(date -> {
                    DayOfWeek day = date.getDayOfWeek();
                    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
                })
                .count();
    }

    private void ensureNoOverlappingRequests(LocalDate startDate, LocalDate endDate, User employee) {
        boolean hasOverlaps = vacationRequestRepository.findByEmployee(employee).stream()
                .anyMatch(request -> request.getStatus() != VacationReqStatus.Rejected &&
                        !(endDate.isBefore(request.getStartDate()) || startDate.isAfter(request.getEndDate())));

        if (hasOverlaps) {
            throw new VacationRequestOverlapException("The requested dates overlap with an existing vacation request.");
        }
    }


    private VacationRequest buildVacationRequest(VacationRequestDTO dto, User employee, User administrator) {
        return VacationRequest.builder()
                .employee(employee)
                .administrator(administrator)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .requestedDate(Instant.now())
                .status(VacationReqStatus.New)
                .build();
    }

    private String generatePdf(VacationRequest vacationRequest) {
        String directoryPath = "vacation_requests";
        String filePath = directoryPath + "/vacation_request_" + vacationRequest.getId() + ".pdf";

        // Ensure the directory exists
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new RuntimeException("Error creating directory: " + directoryPath, e);
        }

        Document document = new Document();
        try {

            document.setMargins(72, 72, 72, 72);

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font normalFont = new Font(Font.FontFamily.HELVETICA, 14);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18 , Font.UNDERLINE);

            Paragraph title = new Paragraph("Leave request", titleFont);
            title.setSpacingAfter(20);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            Paragraph tabbedParagraph = new Paragraph("Dear Administrator,", normalFont);
            tabbedParagraph.setIndentationLeft(36); // Adjust this number to control tab space
            tabbedParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(tabbedParagraph);

            document.add(new Paragraph("\n"));

            long vacationDays = calculateWeekdays(vacationRequest.getStartDate(), vacationRequest.getEndDate());
            int year = vacationRequest.getStartDate().getYear();

            String text = String.format(
                    "The undersigned %s %s, employee of the company %s, " +
                            "kindly request your approval of this application through which I request to take %d days " +
                            "of leave for the year %d, during the period %s - %s.\n\n" +
                            "I thank you in advance for your consideration and support.\n\n",
                    vacationRequest.getEmployee().getFirstName(),
                    vacationRequest.getEmployee().getLastName(),
                    vacationRequest.getEmployee().getContract().getCompany().getName(),
                    vacationDays,
                    year,
                    vacationRequest.getStartDate(),
                    vacationRequest.getEndDate()
            );

            Paragraph requestText = new Paragraph(text, normalFont);
            requestText.setLeading(30);
            requestText.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(requestText);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);

            PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + LocalDate.now().toString(), normalFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell handledByCell = new PdfPCell(new Phrase("Handled by: " + vacationRequest.getAdministrator().getFirstName() + " " + vacationRequest.getAdministrator().getLastName(), normalFont));
            handledByCell.setBorder(Rectangle.NO_BORDER);
            handledByCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            handledByCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(dateCell);
            table.addCell(handledByCell);

            document.add(table);

        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        } finally {
            document.close();
        }

        return filePath;
    }

    @Override
    public List<UserDetailsDTO> getAvailableAdministrators(User employee) {
        return userRepository.findUsersByRoleAndCompany(UserRole.Administrator, employee.getContract().getCompany()).stream()
                .map(this::convertToUserDetailsDTO)
                .toList();
    }

    public UserDetailsDTO convertToUserDetailsDTO(User user) {
        UserDetailsDTO.UserDetailsDTOBuilder builder = UserDetailsDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telefonNumber(user.getTelefonNumber())
                .accountLocked(user.isAccountLocked())
                .role(user.getRole());

        return builder.build();
    }

    public List<VacationRequestDetailsDTO> getVacationRequestsByEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getRole() == UserRole.Employee) {
            if (currentUser.getContract() != null) {


                List<VacationRequest> vacationRequests = vacationRequestRepository.findByEmployeeIdOrderByRequestedDateDesc(currentUser.getId());

                return vacationRequests.stream()
                        .map(vacationRequest -> VacationRequestDetailsDTO.builder()
                                .id(vacationRequest.getId())
                                .adminEmail(vacationRequest.getAdministrator().getEmail())
                                .requestedDate(vacationRequest.getRequestedDate())
                                .startDate(vacationRequest.getStartDate())
                                .endDate(vacationRequest.getEndDate())
                                .description(vacationRequest.getDescription())
                                .status(vacationRequest.getStatus())
                                .vacationDays((int) calculateWeekdays(vacationRequest.getStartDate(), vacationRequest.getEndDate()))
                                .build())
                        .collect(Collectors.toList());
            } else {
                throw new RuntimeException("Employee has no contract");
            }
        } else {
            throw new RuntimeException("User is not an employee");
        }
    }

    public List<VacationRequestDetailsDTO> getApprovedVacationRequestsByEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getRole() == UserRole.Employee) {
            if (currentUser.getContract() != null) {
                List<VacationRequest> vacationRequests = vacationRequestRepository.findApprovedVacationRequestsByEmployeeId(currentUser.getId(), VacationReqStatus.Approved);
                return vacationRequests.stream()
                        .map(vacationRequest -> VacationRequestDetailsDTO.builder()
                                .id(vacationRequest.getId())
                                .startDate(vacationRequest.getStartDate())
                                .endDate(vacationRequest.getEndDate())
                                .description(vacationRequest.getDescription())
                                .vacationDays((int) calculateWeekdays(vacationRequest.getStartDate(), vacationRequest.getEndDate()))
                                .build())
                        .collect(Collectors.toList());
            } else {
                throw new RuntimeException("Employee has no contract");
            }
        } else {
            throw new RuntimeException("User is not an employee");
        }
    }

    public void deleteVacationRequest(Integer requestId) {

        VacationRequest vacationRequest = vacationRequestRepository.findById(requestId).orElse(null);
        if (vacationRequest == null) {
            throw new VacationRequestNotFoundException("Vacation request with id " + requestId + " not found.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getRole() == UserRole.Employee) {
            Contract contract = currentUser.getContract();
            if (contract != null) {
                if (vacationRequest.getEmployee().getId() == currentUser.getId()) {
                    contract.setActualYearVacationDays(contract.getActualYearVacationDays() + (int) calculateWeekdays(vacationRequest.getStartDate(), vacationRequest.getEndDate()));
                    contractRepository.save(contract);
                    vacationRequestRepository.deleteById(requestId);
                } else {
                    throw new IllegalArgumentException("the vacation request does not belong to the employee who made the request");
                }
            } else {
                throw new RuntimeException("Employee has no contract");
            }
        } else {
            throw new RuntimeException("User is not an employee");
        }
    }


    public VacationRequest getVacationRequestById(Integer requestId) {
        return vacationRequestRepository.findById(requestId).orElse(null);
    }




}