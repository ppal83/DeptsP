package com.aimprosoft.handlers.dept;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.handlers.util.DeptFromReq;
import com.aimprosoft.model.Dept;
import com.aimprosoft.validation.dept.DeptValidator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

public class DeptUpdateHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(DeptUpdateHandler.class);

    @Override
    public void handle(PortletRequest req, PortletResponse resp,
                       Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException {

        logger.debug("Handling request");

        DeptDAO deptDAO = (DeptDAO) DAOs.get("deptDAO");

        int id = req.getParameter("id") != null ?
                Integer.parseInt(req.getParameter("id")) : 0;

        DeptValidator validator = new DeptValidator();

        if (validator.validate(req)) {
            Dept dept = DeptFromReq.create(req);
            if (id != 0) {
                dept.setId(id);
                deptDAO.updateDept(dept);
            } else deptDAO.addDept(dept);
            logger.info("Forwarding to DeptListHandler");

            ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
            redirectURL.setParameter("action", "/deptlist.html");
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            ((ActionResponse) resp).sendRedirect(redirectURL.toString());

        } else {
            req.setAttribute("errorsBean", validator.getDeptErrBean());
            req.setAttribute("deptInputed", validator.getDeptInputedBean());

            logger.debug("Validation failed");
            ((ActionResponse) resp).setRenderParameter("deptId", "" + id);
            ((ActionResponse) resp).setRenderParameter("action",
                    id == 0 ? "/deptadd_form.html" : "/deptedit_form.html");

        }
    }
}
