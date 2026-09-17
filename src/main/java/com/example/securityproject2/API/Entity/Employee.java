package com.example.securityproject2.API.Entity;

import jakarta.persistence.Entity;

@Entity
public class Employee extends User{

    private Long employeeId;
    private String position;
    private String department;
    private Double salary;
    private String hireDate;
    private String terminationDate;
    private boolean status;
    private String workLocation;
    private String workPhone;
    private String workEmail;

    public Employee() {
    }

    public Employee(Long id, String username, String password, String email) {
        super(id, username, password, email);
    }

    public Employee(String username, String password, String email, Long employeeId, String position, String department, Double salary) {
        super(username, password, email);
        this.employeeId = employeeId;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.status = true;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
}
