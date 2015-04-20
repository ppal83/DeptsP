package com.aimprosoft.handlers.employee;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.model.Dept;
import com.aimprosoft.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeListHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeListHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.info("Handling request");

        EmployeeDAO employeeDAO = (EmployeeDAO) DAOs.get("employeeDAO");
        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        int id = Integer.parseInt(req.getParameter("deptId"));
        Dept dept = deptDAO.getDeptById(id);
        req.setAttribute("dept", dept);

        List<Employee> empsList = employeeDAO.getEmployeesByDeptId(id);
        req.setAttribute("empsList", empsList);

        logger.info("Forwarding to employees.jsp");

        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/employees.jsp");
        prd.forward(req, resp);
    }
}
