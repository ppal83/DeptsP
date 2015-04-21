package com.aimprosoft.DAO;

import com.aimprosoft.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    void addEmployee(Employee emp);
    void deleteEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployeeById(int id);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    Employee findByName(String username);
    Employee findByEmail(String email);

}
