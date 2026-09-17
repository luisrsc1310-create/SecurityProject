package com.example.securityproject2.API.Controller;

import com.example.securityproject2.API.Entity.Employee;
import com.example.securityproject2.API.Services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Listar todos os funcionários ou filtrar por departamento/status
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean status) {
        if (department != null) {
            return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
        }
        if (status != null) {
            return ResponseEntity.ok(employeeService.getEmployeesByStatus(status));
        }
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Buscar funcionário por ID (chave primária herdada)
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar funcionário por matrícula funcional (employeeId)
    @GetMapping("/registration/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable Long employeeId) {
        return employeeService.getEmployeeByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obter resumo formatado dos dados do funcionário
    @GetMapping("/{id}/summary")
    public ResponseEntity<String> getEmployeeSummary(@PathVariable Long id) {
        String summary = employeeService.getEmployeeSummary(id);
        return ResponseEntity.ok(summary);
    }

    // Cadastrar novo funcionário
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Atualizar dados cadastrais e profissionais do funcionário
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updated = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualização de salário
    @PatchMapping("/{id}/salary")
    public ResponseEntity<Employee> updateSalary(@PathVariable Long id, @RequestParam Double salary) {
        try {
            Employee updated = employeeService.updateSalary(id, salary);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Alteração de status (ativo/inativo) e data de rescisão
    @PatchMapping("/{id}/status")
    public ResponseEntity<Employee> changeEmployeeStatus(
            @PathVariable Long id,
            @RequestParam boolean status,
            @RequestParam(required = false) String terminationDate) {
        try {
            Employee updated = employeeService.changeEmployeeStatus(id, status, terminationDate);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
