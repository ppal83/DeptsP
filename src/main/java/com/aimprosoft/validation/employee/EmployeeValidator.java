package com.aimprosoft.validation.employee;

import com.aimprosoft.DAO.DAOFactory;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EmployeeValidator {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeValidator.class);

    private EmployeeErrorsBean empErrBean = new EmployeeErrorsBean();
    private EmployeeInputedBean empInputedBean = null;

    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

    public boolean validate(PortletRequest req) {

        //creating employee bean with string fields for validation
        createEmpInpBean(req);

        logger.debug("Validating employee " + empInputedBean);

        rejectIfEmptyOrWhitespace("Name", empInputedBean.getName());
        rejectIfIncorrectDate("BirthDate", empInputedBean.getBirthDate());
        rejectIfIncorrectDate("HireDate", empInputedBean.getHireDate());
        rejectIfEmptyOrWhitespace("Address", empInputedBean.getAddress());
        rejectIfIncorrectEmail("Email", empInputedBean.getEmail());
        rejectIfEmptyOrWhitespace("Salary", empInputedBean.getSalary());

        rejectIfNameExists();
        rejectIfEmailExists();
        rejectIfSalaryNonNumerous();

        return empErrBean.isValid();
    }

    public EmployeeErrorsBean getEmpErrBean() {
        return empErrBean;
    }

    public EmployeeInputedBean getEmpInputedBean() {
        return empInputedBean;
    }

    private void createEmpInpBean(PortletRequest req) {
        String id = req.getParameter("id") != null ? req.getParameter("id") : "0";
        String name = req.getParameter("name");
        String birthDate = req.getParameter("Birthday");
        String hireDate = req.getParameter("HireDate");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String salary = req.getParameter("salary");

        empInputedBean = new EmployeeInputedBean(id, name, birthDate, hireDate, address, email, salary);
    }

    private void rejectIfEmptyOrWhitespace(String inputFieldName, String inputFieldValue) {
        if ( inputFieldValue.isEmpty() || inputFieldValue.trim().isEmpty() ) {
            setErrBeanProperty(inputFieldName, "Employee " + inputFieldName + " is required");
            empErrBean.setValid(false);
        }
    }

    private void rejectIfIncorrectDate(String inputFieldName, String inputFieldValue) {
        if ( !inputFieldValue.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            setErrBeanProperty(inputFieldName, "Date is empty or not well formed");
            empErrBean.setValid(false);
        }
    }

    private void rejectIfIncorrectEmail(String inputFieldName, String inputFieldValue) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if ( !inputFieldValue.matches(regex)) {
            setErrBeanProperty(inputFieldName, "Email is empty or not well formed");
            empErrBean.setValid(false);
        }
    }

    private void rejectIfNameExists() {

        int empId = Integer.parseInt(empInputedBean.getId());
        List<Employee> empsList = employeeDAO.getAllEmployees();

        //new employee
        if (empId == 0) {
            for (Employee employee : empsList) {
                if ( empInputedBean.getName().equals(employee.getName()) ) {
                    empErrBean.setName("Employee with this name already exists");
                    empErrBean.setValid(false);
                }
            }
        }

        //existing employee
        if (empId != 0) {
            Employee empDb = employeeDAO.getEmployeeById(empId);
            for (Employee employee : empsList) {
                if ( empInputedBean.getName().equals(employee.getName())
                        && !empInputedBean.getName().equals(empDb.getName()) ) {
                    empErrBean.setName("Employee with this name already exists");
                    empErrBean.setValid(false);
                }
            }
        }
    }

    private void rejectIfEmailExists() {

        int empId = Integer.parseInt(empInputedBean.getId());
        List<Employee> empsList = employeeDAO.getAllEmployees();

        //new employee
        if (empId == 0) {
            for (Employee employee : empsList) {
                if ( empInputedBean.getEmail().equals(employee.getEmail()) ) {
                    empErrBean.setEmail("Employee with this email already exists");
                    empErrBean.setValid(false);
                }
            }
        }

        //existing employee
        if (empId != 0) {
            Employee empDb = employeeDAO.getEmployeeById(empId);
            for (Employee employee : empsList) {
                if ( empInputedBean.getEmail().equals(employee.getEmail())
                        && !empInputedBean.getEmail().equals(empDb.getEmail()) ) {
                    empErrBean.setEmail("Employee with this email already exists");
                    empErrBean.setValid(false);
                }
            }
        }

    }

    private void rejectIfSalaryNonNumerous() {
        if ( !empInputedBean.getSalary().matches("\\d+(\\.\\d+)?") ) {
            empErrBean.setSalary("Wrong Salary");
            empErrBean.setValid(false);
        }
    }

    private void setErrBeanProperty(String errFieldName, String errFieldValue) {
        try {
            Method setter = empErrBean.getClass().getMethod("set" + errFieldName, String.class);
            setter.invoke(empErrBean, errFieldValue);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
