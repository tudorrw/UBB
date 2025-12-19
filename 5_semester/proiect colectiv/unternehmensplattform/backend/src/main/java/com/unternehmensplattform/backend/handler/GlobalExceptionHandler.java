package com.unternehmensplattform.backend.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                    ExceptionResponse.builder()
                            .validationErrors(errors)
                            .build()
                );
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal server, contact the superadmin")
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(DuplicatePhoneNumberException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicatePhoneNumberException(DuplicatePhoneNumberException exp) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.PHONE_NUMBER_ALREADY_EXISTS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.PHONE_NUMBER_ALREADY_EXISTS.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(DuplicateEmailException.class)
        public ResponseEntity<ExceptionResponse> handleDuplicateEmailException(DuplicateEmailException exp) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.EMAIL_ALREADY_EXISTS.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.EMAIL_ALREADY_EXISTS.getDescription())
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

    @ExceptionHandler(DuplicateNameException.class)
        public ResponseEntity<ExceptionResponse> handleDuplicateNameException(DuplicateNameException exp) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.NAME_ALREADY_EXISTS.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.NAME_ALREADY_EXISTS.getDescription())
                                    .error(exp.getMessage())
                                    .build()
                    );
            }
    @ExceptionHandler(NoAdminProvidedException.class)
        public ResponseEntity<ExceptionResponse> handleNoAdminProvidedException(NoAdminProvidedException exp) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.NO_ADMIN_PROVIDED.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.NO_ADMIN_PROVIDED.getDescription())
                                    .error(exp.getMessage())
                                    .build()
                    );
            }

    @ExceptionHandler(VacationRequestNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleVacationRequestNotFound(VacationRequestNotFoundException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.NO_LEAVE_REQUEST_PROVIDED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.NO_LEAVE_REQUEST_PROVIDED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(VacationRequestOverlapException.class)
    public ResponseEntity<ExceptionResponse> handleVacationRequestOverlapException(VacationRequestOverlapException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.LEAVE_REQUEST_OVERLAP.getCode())
                                .businessErrorDescription(BusinessErrorCodes.LEAVE_REQUEST_OVERLAP.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(VacationRequestValidationDatesException.class)
    public ResponseEntity<ExceptionResponse> handleVacationRequestValidationDatesException(VacationRequestValidationDatesException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.VALIDATE_DATES.getCode())
                                .businessErrorDescription(BusinessErrorCodes.VALIDATE_DATES.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(InvalidVacationRequestException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidVacationRequestException(InvalidVacationRequestException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.LEAVE_REQUEST_INVALID.getCode())
                                .businessErrorDescription(BusinessErrorCodes.LEAVE_REQUEST_INVALID.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(WorkingDaysOverlapException.class)
    public ResponseEntity<ExceptionResponse> handleWorkingDaysOverlapException(WorkingDaysOverlapException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACTIVITY_REPORT_OVERLAP.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACTIVITY_REPORT_OVERLAP.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(WDOverlapWithVDException.class)
    public ResponseEntity<ExceptionResponse> handleWDOverlapWithVDException(WDOverlapWithVDException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.WD_OVERLAP_WITH_VD.getCode())
                                .businessErrorDescription(BusinessErrorCodes.WD_OVERLAP_WITH_VD.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ArrivalBeforeLeaveException.class)
    public ResponseEntity<ExceptionResponse> handleArrivalBeforeDepartureException(ArrivalBeforeLeaveException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ARRIVAL_BEFORE_LEAVE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ARRIVAL_BEFORE_LEAVE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(WorkingDaySameDatesException.class)
    public ResponseEntity<ExceptionResponse> handleWorkingDaySameDatesException(WorkingDaySameDatesException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ALL_DATES_SAME.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ALL_DATES_SAME.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(VROverlapsWithWDException.class)
    public ResponseEntity<ExceptionResponse> handleVROverlapsWithWD(VROverlapsWithWDException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.VD_OVERLAP_WITH_WD.getCode())
                                .businessErrorDescription(BusinessErrorCodes.VD_OVERLAP_WITH_WD.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(WorkingDayBeforeSigningDate.class)
    public ResponseEntity<ExceptionResponse> handleWorkingDayBeforeSigningDate(WorkingDayBeforeSigningDate exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.WD_BEFORE_SIGNING_DATE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.WD_BEFORE_SIGNING_DATE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

}
