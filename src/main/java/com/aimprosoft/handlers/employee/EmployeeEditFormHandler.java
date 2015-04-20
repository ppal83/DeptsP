package com.aimprosoft.handlers.employee;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.model.Dept;
import com.aimprosoft.model.Employee;
import com.aimprosoft.validation.employee.EmployeeInputedBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeEditFormHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEditFormHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        EmployeeDAO employeeDAO = (EmployeeDAO) DAOs.get("employeeDAO");
        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        int id = Integer.parseInt(req.getParameter("id"));

        //receiving attribute from processAction() needed for rendering
        //form without loosing inputed data during validation
        if (req.getAttribute("employeeInputed") != null) {
            req.setAttribute("employee", req.getAttribute("employeeInputed"));
        } else {
            req.setAttribute("employee", employeeDAO.getEmployeeById(id));
        }

        List<Dept> deptsList = deptDAO.getAllDepts();
        req.setAttribute("deptsList", deptsList);

        logger.info("Forwarding to employee_edit.jsp");
        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/employee_edit.jsp");
        prd.forward(req, resp);
    }
}