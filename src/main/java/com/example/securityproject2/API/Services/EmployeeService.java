package com.example.securityproject2.API.Services;

import com.example.securityproject2.API.Entity.Employee;
import com.example.securityproject2.API.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    // Injeção de dependências do repositório de funcionários e do serviço de usuários
    public EmployeeService(EmployeeRepository employeeRepository, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    // Retorna todos os funcionários cadastrados
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Busca um funcionário pelo ID da entidade (chave primária herdada de User)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Busca um funcionário pelo identificador/matrícula funcional (employeeId)
    public Optional<Employee> getEmployeeByEmployeeId(Long employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }

    // Busca funcionários filtrando por departamento
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    // Busca funcionários filtrando por status (ativos ou inativos)
    public List<Employee> getEmployeesByStatus(boolean status) {
        return employeeRepository.findByStatus(status);
    }

    // Salva ou atualiza uma entidade Employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Cria um novo Employee recebendo os atributos principais
    public Employee createEmployee(String username, String password, String email,
        Long employeeId, String position, String department, Double salary) {
        Employee employee = new Employee(username, password, email, employeeId, position, department, salary);
        return employeeRepository.save(employee);
    }

    // Atualiza os dados de um funcionário existente (dados pessoais herdados e dados profissionais)
    public Employee updateEmployee(Long id, Employee updatedData) {
        return employeeRepository.findById(id).map(employee -> {
            // Atualiza campos herdados de User
            if (updatedData.getUsername() != null) {
                employee.setUsername(updatedData.getUsername());
            }
            if (updatedData.getEmail() != null) {
                employee.setEmail(updatedData.getEmail());
            }
            if (updatedData.getPassword() != null) {
                employee.setPassword(updatedData.getPassword());
            }

            // Atualiza campos específicos de Employee
            if (updatedData.getEmployeeId() != null) {
                employee.setEmployeeId(updatedData.getEmployeeId());
            }
            if (updatedData.getPosition() != null) {
                employee.setPosition(updatedData.getPosition());
            }
            if (updatedData.getDepartment() != null) {
                employee.setDepartment(updatedData.getDepartment());
            }
            if (updatedData.getSalary() != null) {
                employee.setSalary(updatedData.getSalary());
            }
            if (updatedData.getHireDate() != null) {
                employee.setHireDate(updatedData.getHireDate());
            }
            if (updatedData.getTerminationDate() != null) {
                employee.setTerminationDate(updatedData.getTerminationDate());
            }
            if (updatedData.getWorkLocation() != null) {
                employee.setWorkLocation(updatedData.getWorkLocation());
            }
            if (updatedData.getWorkPhone() != null) {
                employee.setWorkPhone(updatedData.getWorkPhone());
            }
            if (updatedData.getWorkEmail() != null) {
                employee.setWorkEmail(updatedData.getWorkEmail());
            }
            employee.setStatus(updatedData.isStatus());

            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));
    }

    // Atualiza o salário de um funcionário
    public Employee updateSalary(Long id, Double newSalary) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setSalary(newSalary);
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));
    }

    // Altera o status do funcionário (ex: desativação/demissão ou reativação)
    public Employee changeEmployeeStatus(Long id, boolean status, String terminationDate) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setStatus(status);
            if (!status && terminationDate != null) {
                employee.setTerminationDate(terminationDate);
            }
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o ID: " + id));
    }

    // Retorna um resumo detalhado contendo informações de User e Employee
    public String getEmployeeSummary(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee emp = optionalEmployee.get();
            return String.format(
                    "ID: %d | Matrícula: %d | Usuário: %s | Email: %s | Cargo: %s | Depto: %s | Salário: R$ %.2f | Ativo: %s",
                    emp.getId(),
                    emp.getEmployeeId(),
                    emp.getUsername(),
                    emp.getEmail(),
                    emp.getPosition(),
                    emp.getDepartment(),
                    emp.getSalary() != null ? emp.getSalary() : 0.0,
                    emp.isStatus() ? "Sim" : "Não"
            );
        }

        return "Funcionário não encontrado";
    }

    // Deleta um funcionário por ID
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
