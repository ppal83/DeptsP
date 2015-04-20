package com.aimprosoft.handlers.dept;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.model.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DeptListHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(DeptListHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");
        List<Dept> deptsList = deptDAO.getAllDepts();
        req.setAttribute("deptsList", deptsList);

        logger.debug("Forwarding to depts.jsp");
        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/depts.jsp");
        prd.forward(req, resp);
    }
}
