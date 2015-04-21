package com.aimprosoft;

import com.aimprosoft.model.Dept;
import com.aimprosoft.model.Employee;
import com.aimprosoft.services.DeptService;
import com.aimprosoft.services.EmployeeService;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("VIEW")
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private DeptService deptService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping  // default (action=index)
    public String renderIndexPage(Model model) {
        model.addAttribute("deptsList", deptService.getAllDepts());

        logger.info("Rendering index.jsp");
        return "index";
    }

    @RequestMapping(params = "action=/emplist.html")
    public String renderEmpList(@RequestParam("deptId") int id, Model model) {
        Dept dept = deptService.getDeptById(id);
        model.addAttribute("dept", dept);
        Set<Employee> empsList = dept.getEmps();
        model.addAttribute("empsList", empsList);

        logger.info("Rendering employees.jsp");
        return "employees";
    }

    @RequestMapping(params = "action=/emplist.html")
    public void actionEmpList(@RequestParam("deptId") int id, ActionResponse resp) {
        resp.setRenderParameter("deptId", "" + id);
        resp.setRenderParameter("action", "/emplist.html");
    }

    @RequestMapping(params = "action=/deptlist.html")
    public String renderDeptList(Model model) {
        List<Dept> deptsList = deptService.getAllDepts();
        model.addAttribute("deptsList", deptsList);

        logger.info("Rendering depts.jsp");
        return "depts";
    }

    @RequestMapping(params = "action=/deptlist.html")
    public void actionDeptList(ActionResponse resp) {
        resp.setRenderParameter("action", "/deptlist.html");
    }

    @RequestMapping(params = "action=/deptdel.html")
    public void actionDeptDelete(@RequestParam("deptId") int id,
                                 ActionRequest req, ActionResponse resp) throws WindowStateException, IOException {
        deptService.deleteDeptById(id);
        //resp.setRenderParameter("action", "/deptlist.html");

        logger.info("Redirecting to depts.jsp");
        ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
        String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
        PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
        redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
        redirectURL.setParameter("action", "/deptlist.html");
        redirectURL.setWindowState(WindowState.MAXIMIZED);
        resp.sendRedirect(redirectURL.toString());
        logger.info("Redirect link generated" + redirectURL.toString());
    }

    @RequestMapping(params = "action=/empdel.html")
    public void actionEmpDelete(@RequestParam("id") int id,
                                ActionRequest req, ActionResponse resp) throws WindowStateException, IOException {
        int deptId = employeeService.getEmployeeById(id).getDept().getId();
        employeeService.deleteEmployeeById(id);

        logger.info("Redirecting to employees.jsp");
        ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
        String portletName = (String)req.getAttribute(WebKeys.PORTLET_ID);
        PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
        redirectURL.setParameter("deptId", "" + deptId);
        redirectURL.setParameter("jspPage", "/WEB-INF/views/employees.jsp");
        redirectURL.setParameter("action", "/emplist.html");
        redirectURL.setWindowState(WindowState.MAXIMIZED);
        resp.sendRedirect(redirectURL.toString());
        logger.info("Redirect link generated" + redirectURL.toString());
    }

    @RequestMapping(params = "action=/deptadd_form.html")
    public String renderDeptAddForm() {
        logger.info("Rendering dept_add.jsp");
        return "dept_add";
    }

    @RequestMapping(params = "action=/deptadd_form.html")
    public void actionDeptAddForm(ActionResponse resp) {
        resp.setRenderParameter("action", "/deptadd_form.html");
    }

    @RequestMapping(params = "action=/deptadd.html")
    public void actionDeptAdd(@RequestParam("id") int id,
                              @ModelAttribute("dept") Dept dept, ActionResponse resp) {
        if (id != 0) {
            deptService.updateDept(dept);
        } else {
            deptService.addDept(dept);
        }

        resp.setRenderParameter("action", "/deptlist.html");
    }


}
