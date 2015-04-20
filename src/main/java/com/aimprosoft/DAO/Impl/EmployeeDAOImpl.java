package com.aimprosoft.DAO.Impl;

import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.connection.DBCPDataSourceFactory;
import com.aimprosoft.model.Employee;
import com.aimprosoft.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    private static final String ADD_QUERY = "INSERT INTO employee (name, birth_date, "
            + "hire_date, address, email, dept_id, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM employee WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE employee SET name = ?, "
            + "birth_date = ?, hire_date = ?, address = ?, email = ?, dept_id = ?, salary = ? "
            + "WHERE id = ?";
    public static final String REMOVE_BY_DEPTID_QUERY = "DELETE FROM employee WHERE dept_id = ?";
    private static final String GET_QUERY = "SELECT * FROM employee WHERE id = ?";
    private static final String GET_BY_DEPTID_QUERY = "SELECT * FROM employee WHERE dept_id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM employee";

    @Override
    public void addEmployee(Employee emp) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(ADD_QUERY)) {
                stat.setString(1, emp.getName());
                stat.setDate(2, new java.sql.Date(emp.getBirthDate().getTime()));
                stat.setDate(3, new java.sql.Date(emp.getHireDate().getTime()));
                stat.setString(4, emp.getAddress());
                stat.setString(5, emp.getEmail());
                stat.setInt(6, emp.getDeptId());
                stat.setInt(7, emp.getSalary());
                int num = stat.executeUpdate();
                logger.info(num + " row succefully added");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public void deleteEmployee(Employee emp) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(DELETE_QUERY)) {
                stat.setInt(1, emp.getId());
                int num = stat.executeUpdate();
                logger.info(num + " row succefully deleted");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public void updateEmployee(Employee emp) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(UPDATE_QUERY)) {
                stat.setString(1, emp.getName());
                stat.setDate(2, new java.sql.Date(emp.getBirthDate().getTime()));
                stat.setDate(3, new java.sql.Date(emp.getHireDate().getTime()));
                stat.setString(4, emp.getAddress());
                stat.setString(5, emp.getEmail());
                stat.setInt(6, emp.getDeptId());
                stat.setInt(7, emp.getSalary());
                stat.setInt(8, emp.getId());
                int num = stat.executeUpdate();
                logger.info(num + " row succefully updated");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(DELETE_QUERY)) {
                stat.setInt(1, id);
                int num = stat.executeUpdate();
                logger.info(num + " row succefully deleted");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public void deleteEmployeesByDeptId(int deptId) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(REMOVE_BY_DEPTID_QUERY)) {
                stat.setInt(1, deptId);
                int num = stat.executeUpdate();
                logger.info(num + " row(s) succefully deleted");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee emp = null;
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(GET_QUERY)) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    rs.next();
                    String name = rs.getString(2);
                    Date birthDate = new Date(rs.getDate(3).getTime());
                    Date hireDate = new Date(rs.getDate(4).getTime());
                    String address = rs.getString(5);
                    String email = rs.getString(6);
                    int deptId = rs.getInt(7);
                    int salary = rs.getInt(8);

                    emp = new Employee(name, birthDate, hireDate, address,
                            email, deptId, salary);
                    emp.setId(id);
                }
                logger.info(emp + " was fetched from db");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return emp;
    }

    @Override
    public List<Employee> getEmployeesByDeptId(int deptId) {
        List<Employee> employeesList = new ArrayList<>();
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(GET_BY_DEPTID_QUERY)) {
                stat.setInt(1, deptId);
                try (ResultSet rs = stat.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(2);
                        Date birthDate = new Date(rs.getDate(3).getTime());
                        Date hireDate = new Date(rs.getDate(4).getTime());
                        String address = rs.getString(5);
                        String email = rs.getString(6);
                        deptId = rs.getInt(7);
                        int salary = rs.getInt(8);

                        Employee emp = new Employee(name, birthDate, hireDate, address,
                                email, deptId, salary);
                        int id = rs.getInt(1);
                        emp.setId(id);
                        employeesList.add(emp);
                    }
                    logger.info(employeesList + " list was fetched from db");
                }
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return employeesList;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeesList = new ArrayList<>();
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(GET_ALL_QUERY)) {
                try (ResultSet rs = stat.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(2);
                        Date birthDate = new Date(rs.getDate(3).getTime());
                        Date hireDate = new Date(rs.getDate(4).getTime());
                        String address = rs.getString(5);
                        String email = rs.getString(6);
                        int deptId = rs.getInt(7);
                        int salary = rs.getInt(8);

                        Employee emp = new Employee(name, birthDate, hireDate, address,
                                email, deptId, salary);
                        int id = rs.getInt(1);
                        emp.setId(id);
                        employeesList.add(emp);
                    }
                    logger.info(employeesList + " list was fetched from db");
                }
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return employeesList;
    }
}