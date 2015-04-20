package com.aimprosoft.DAO;

import com.aimprosoft.DAO.Impl.DeptDAOImpl;
import com.aimprosoft.DAO.Impl.EmployeeDAOImpl;

public class DAOFactory {

    private static EmployeeDAO emplDAO;
    private static DeptDAO deptDAO;


    private static DAOFactory instance;

    private DAOFactory() {

    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }


    public EmployeeDAO getEmployeeDAO() {
        if (emplDAO == null) {
            emplDAO = new EmployeeDAOImpl();
        }
        return emplDAO;
    }

    public DeptDAO getDeptDAO() {
        if (deptDAO == null) {
            deptDAO = new DeptDAOImpl();
        }
        return deptDAO;
    }
}
