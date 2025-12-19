package com.unternehmensplattform.backend.repositories;

import java.util.List;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.entities.VacationRequest;
import com.unternehmensplattform.backend.enums.VacationReqStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacationReqRepository extends JpaRepository<VacationRequest, Integer> {

    List<VacationRequest> findByEmployeeIdOrderByRequestedDateDesc(Integer employeeId);


    void deleteById(Integer id);
    List<VacationRequest> findByAdministratorId(Integer administratorId);

    @Query("SELECT vr FROM VacationRequest vr " +
            "WHERE vr.administrator.id = :adminId " +
            "AND vr.status = :status " +
            "ORDER BY vr.startDate DESC")
    List<VacationRequest> findByAdministratorIdAndStatus(Integer adminId, VacationReqStatus status);

    @Query("SELECT vr FROM VacationRequest vr " +
            "WHERE vr.administrator.id = :adminId " +
            "AND (vr.status = :status1 OR vr.status = :status2) " +
            "ORDER BY vr.startDate DESC")
    List<VacationRequest> findByAdministratorIdAndStatusInOrderByStartDateDesc(
            Integer adminId, VacationReqStatus status1, VacationReqStatus status2);

    List<VacationRequest> findByEmployee(User employee);

    int countByAdministratorAndStatus(User administrator, VacationReqStatus status);
    @Query("SELECT vr FROM VacationRequest vr WHERE vr.employee.id = :employeeId AND vr.status = :status ORDER BY vr.startDate DESC")
    List<VacationRequest> findApprovedVacationRequestsByEmployeeId(Integer employeeId, VacationReqStatus status) ;
}
