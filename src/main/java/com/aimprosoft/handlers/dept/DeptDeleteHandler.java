package com.aimprosoft.handlers.dept;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.model.Dept;
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

public class DeptDeleteHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(DeptDeleteHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");
        EmployeeDAO employeeDAO = (EmployeeDAO) DAOs.get("employeeDAO");
        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        int id = Integer.parseInt(req.getParameter("deptId"));
        employeeDAO.deleteEmployeesByDeptId(id);
        deptDAO.deleteDeptById(id);
        List<Dept> deptsList = deptDAO.getAllDepts();
        req.setAttribute("deptsList", deptsList);
        logger.info("Forwarding to depts.jsp");
        ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
        String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
        PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
        redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
        redirectURL.setParameter("action", "/deptlist.html");
        redirectURL.setWindowState(WindowState.MAXIMIZED);
        ((ActionResponse) resp).sendRedirect(redirectURL.toString());

    }
}