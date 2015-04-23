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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.portlet.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("VIEW")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private DeptService deptService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("employeeValidator")
    private Validator validator;

    @InitBinder("employee")
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
        binder.setValidator(validator);
    }

    //-------------------------------emplist.html-----------------------------------------

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

    //-------------------------------empdel.html-----------------------------------------

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

    //-------------------------------empadd_form.html-----------------------------------------

    @RequestMapping(params = "action=/empadd_form.html")
    public String renderEmpAddForm(Model model) {
        logger.info("Rendering emp_add.jsp");

        //if attribute "emp" exist in model after actionEmployeeAddForm(there were some errors)
        //return it to page displaying errors, else return new Employee
        Employee emp;
        if (model.containsAttribute("employee")) {
            emp = (Employee) model.asMap().get("employee");
        } else {
            emp = new Employee();
        }
        model.addAttribute(emp);
        model.addAttribute("deptsList", deptService.getAllDepts());

        return "employee_add";
    }

    @RequestMapping(params = "action=/empadd_form.html")
    public void actionEmpAddForm(ActionResponse resp) {

        resp.setRenderParameter("action", "/empadd_form.html");
    }

    //-------------------------------empadd.html-----------------------------------------

    @RequestMapping(params = "action=/empadd.html")
    public void actionDeptAdd(@ModelAttribute("employee") @Validated Employee emp,
                              BindingResult bindingResult, @RequestParam("deptId") int id,
                              ActionRequest req, ActionResponse resp) throws IOException, WindowStateException {
        logger.info("Received employee bean from form: " + emp);

        if (bindingResult.hasErrors()) {
            logger.info("Validation failed. BindingResult: " + bindingResult);
            logger.info("Returning current page");
            resp.setRenderParameter("action", "/empadd_form.html");
        } else {
            logger.info("Validation succesfull " + emp);
            emp.setDept(deptService.getDeptById(id));
            employeeService.addEmloyee(emp);

            logger.info("Redirecting to employees.jsp");
            ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String) req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
            redirectURL.setParameter("action", "/emplist.html");
            redirectURL.setParameter("deptId", "" + emp.getDept().getId());
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            resp.sendRedirect(redirectURL.toString());
            logger.info("Redirect link generated" + redirectURL.toString());
        }
    }

    //-------------------------------empedit_form.html-----------------------------------------

    @RequestMapping(params = "action=/empedit_form.html")
    public String renderEmpEditForm(@RequestParam("id") int id, Model model) {
        logger.info("Rendering emp_edit.jsp");

        //if attribute "emp" exist in model after actionDeptEditForm(there were some errors)
        //return it to page displaying errors, else return employee by id
        Employee emp;
        if (model.containsAttribute("employee")) {
            emp = (Employee) model.asMap().get("employee");
        } else {
            emp = employeeService.getEmployeeById(id);
        }
        model.addAttribute(emp);
        model.addAttribute("deptsList", deptService.getAllDepts());

        return "employee_edit";
    }

    @RequestMapping(params = "action=/empedit_form.html")
    public void actionEmpEditForm(@RequestParam("id") int id, ActionResponse resp) {
        resp.setRenderParameter("id", "" + id);
        resp.setRenderParameter("action", "/empedit_form.html");
    }

    //-------------------------------empedit.html-----------------------------------------

    @RequestMapping(params = "action=/empedit.html")
    public void actionDeptEdit(@ModelAttribute("employee") @Validated Employee emp,
                              BindingResult bindingResult, @RequestParam("deptId") int id,
                              ActionRequest req, ActionResponse resp) throws IOException, WindowStateException {
        logger.info("Received employee bean from form: " + emp);

        if (bindingResult.hasErrors()) {
            logger.info("Validation failed. BindingResult: " + bindingResult);
            logger.info("Returning current page");
            resp.setRenderParameter("id", "" + emp.getId());
            resp.setRenderParameter("action", "/empedit_form.html");
        } else {
            logger.info("Validation succesfull " + emp);
            emp.setDept(deptService.getDeptById(id));
            employeeService.updateEmployee(emp);

            logger.info("Redirecting to employees.jsp");
            ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String) req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/employees.jsp");
            redirectURL.setParameter("action", "/emplist.html");
            redirectURL.setParameter("deptId", "" + emp.getDept().getId());
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            resp.sendRedirect(redirectURL.toString());
            logger.info("Redirect link generated" + redirectURL.toString());
        }
    }

}
