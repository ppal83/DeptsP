package com.aimprosoft.handlers.employee;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.handlers.util.EmployeeFromReq;
import com.aimprosoft.model.Dept;
import com.aimprosoft.model.Employee;
import com.aimprosoft.validation.employee.EmployeeValidator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeUpdateHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeUpdateHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");
        EmployeeDAO employeeDAO = (EmployeeDAO) DAOs.get("employeeDAO");

        int id = req.getParameter("id") != null ?
                Integer.parseInt(req.getParameter("id")) : 0;
        int deptId = Integer.parseInt(req.getParameter("deptId"));

        EmployeeValidator validator = new EmployeeValidator();

        if (validator.validate(req)) {
            Employee emp = EmployeeFromReq.create(req);
            if (id != 0) {
                emp.setId(id);
                employeeDAO.updateEmployee(emp);
            } else {
                employeeDAO.addEmployee(emp);
            }

            logger.info("Forwarding to EmployeeListHandler");
            ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/employees.jsp");
            redirectURL.setParameter("deptId", "" + deptId);
            redirectURL.setParameter("action", "/emplist.html");
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            ((ActionResponse) resp).sendRedirect(redirectURL.toString());

        } else {
            req.setAttribute("errorsBean", validator.getEmpErrBean());
            req.setAttribute("employeeInputed", validator.getEmpInputedBean());
            req.setAttribute("id", deptId);

            List<Dept> deptsList = deptDAO.getAllDepts();
            req.setAttribute("deptsList", deptsList);

            logger.info("Validation failed");
            ((ActionResponse) resp).setRenderParameter("id", "" + id);
            ((ActionResponse) resp).setRenderParameter("action",
                    id == 0 ? "/empadd_form.html" : "/empedit_form.html");

        }
    }
}