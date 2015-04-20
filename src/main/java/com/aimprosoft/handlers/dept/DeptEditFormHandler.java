package com.aimprosoft.handlers.dept;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.handlers.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

public class DeptEditFormHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(DeptEditFormHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        int id = Integer.parseInt(req.getParameter("deptId"));

        //receiving attribute from processAction() needed for rendering
        //form without loosing inputed data during validation
        if (req.getAttribute("deptInputed") != null) {
            req.setAttribute("dept", req.getAttribute("deptInputed"));
        } else {
            req.setAttribute("dept", deptDAO.getDeptById(id));
        }

        logger.info("Forwarding to dept_edit.jsp");

        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/dept_edit.jsp");
        prd.forward(req, resp);
    }
}
