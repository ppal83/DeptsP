package com.aimprosoft.DAO;

import com.aimprosoft.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    void addEmployee(Employee emp);
    void deleteEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployeeById(int id);
    void deleteEmployeesByDeptId(int deptId);
    Employee getEmployeeById(int id);
    List<Employee> getEmployeesByDeptId(int deptId);
    List<Employee> getAllEmployees();

}
