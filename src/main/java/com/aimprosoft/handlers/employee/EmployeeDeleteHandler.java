package com.aimprosoft.handlers.employee;

import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

public class EmployeeDeleteHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDeleteHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        EmployeeDAO employeeDAO = (EmployeeDAO) DAOs.get("employeeDAO");

        int id = Integer.parseInt(req.getParameter("id"));
        //int deptId = employeeDAO.getEmployeeById(id).getDeptId();
        employeeDAO.deleteEmployeeById(id);

        logger.info("Forwarding to EmployeeListHandler");
        ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
        String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
        PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
        redirectURL.setParameter("jspPage", "/WEB-INF/views/employees.jsp");
        //redirectURL.setParameter("deptId", "" + deptId);
        redirectURL.setParameter("action", "/emplist.html");
        redirectURL.setWindowState(WindowState.MAXIMIZED);
        ((ActionResponse) resp).sendRedirect(redirectURL.toString());

    }
}
