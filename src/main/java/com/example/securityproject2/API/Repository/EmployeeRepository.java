package com.example.securityproject2.API.Repository;

import com.example.securityproject2.API.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeId(Long employeeId);

    Optional<Employee> findByWorkEmail(String workEmail);

    List<Employee> findByDepartment(String department);

    List<Employee> findByPosition(String position);

    List<Employee> findByStatus(boolean status);

    boolean existsByEmployeeId(Long employeeId);
}
