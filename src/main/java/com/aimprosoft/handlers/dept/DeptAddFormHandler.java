package com.aimprosoft.handlers.dept;

import com.aimprosoft.handlers.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

public class DeptAddFormHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(DeptAddFormHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        //receiving attribute from processAction() needed for rendering
        //form without loosing inputed data during validation
        if (req.getAttribute("deptInputed") != null) {
            req.setAttribute("dept", req.getAttribute("deptInputed"));
        }

        logger.info("Forwarding to dept_add.jsp");
        PortletRequestDispatcher prd = pc.getRequestDispatcher("/WEB-INF/views/dept_add.jsp");
        prd.forward(req, resp);
    }
}