package com.aimprosoft.handlers.employee;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.model.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeAddForm implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeAddForm.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        List<Dept> deptsList = deptDAO.getAllDepts();
        req.setAttribute("deptsList", deptsList);

        //receiving attribute from processAction() needed for rendering
        //form without loosing inputed data during validation
        if (req.getAttribute("employeeInputed") != null) {
            req.setAttribute("employee", req.getAttribute("employeeInputed"));
        }

        logger.info("Forwarding to employee_add.jsp");
        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/employee_add.jsp");
        prd.forward(req, resp);
    }
}