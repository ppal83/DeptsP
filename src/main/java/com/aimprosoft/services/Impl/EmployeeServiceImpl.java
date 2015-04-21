package com.aimprosoft.services.Impl;

import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.model.Employee;
import com.aimprosoft.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("emplService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired(required=true)
	@Qualifier(value="employeeDAO")
	private EmployeeDAO employeeDAO;

	@Override
	@Transactional
	public void addEmloyee(Employee emp) {
		employeeDAO.addEmployee(emp);
	}

	@Override
	@Transactional
	public void deleteEmployee(Employee emp) {
		employeeDAO.deleteEmployee(emp);
	}

	@Override
	@Transactional
	public void updateEmployee(Employee emp) {
		employeeDAO.updateEmployee(emp);
	}

	@Override
	@Transactional
	public Employee getEmployeeById(int id) {
		return employeeDAO.getEmployeeById(id);
	}

	@Override
	@Transactional
	public void deleteEmployeeById(int id) {
		employeeDAO.deleteEmployeeById(id);
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployees() {
		return employeeDAO.getAllEmployees();
	}

	@Override
	@Transactional
	public Employee findByName(String name) {
		return employeeDAO.findByName(name);
	}

	@Override
	@Transactional
	public Employee findByEmail(String email) {
		return employeeDAO.findByEmail(email);
	}

}
