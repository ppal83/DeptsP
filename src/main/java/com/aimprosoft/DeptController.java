package com.aimprosoft;

import com.aimprosoft.model.Dept;
import com.aimprosoft.services.DeptService;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.List;

@Controller
@RequestMapping("VIEW")
public class DeptController {


    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    @Autowired
    @Qualifier("deptValidator")
    private Validator validator;

    @InitBinder("dept")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping
    public String renderIndexPage(Model model) {
        model.addAttribute("deptsList", deptService.getAllDepts());

        logger.info("Rendering index.jsp");
        return "index";
    }

    //-------------------------------deptlist.html-----------------------------------------

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

    //-------------------------------deptdel.html-----------------------------------------

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

    //-------------------------------deptadd_form.html-----------------------------------------

    @RequestMapping(params = "action=/deptadd_form.html")
    public String renderDeptAddForm(Model model) {
        logger.info("Rendering dept_add.jsp");

        //if attribute "dept" exist in model after actionDeptAddForm(there were some errors)
        //return it to page displaying errors, else return new Dept()
        Dept dept;
        if (model.containsAttribute("dept")) {
            dept = (Dept) model.asMap().get("dept");
        } else {
            dept = new Dept();
        }
        model.addAttribute(dept);

        return "dept_add";
    }

    @RequestMapping(params = "action=/deptadd_form.html")
    public void actionDeptAddForm(ActionResponse resp) {

        resp.setRenderParameter("action", "/deptadd_form.html");
    }

    //-------------------------------deptadd.html-----------------------------------------

    @RequestMapping(params = "action=/deptadd.html")
    public void actionDeptAdd(@ModelAttribute("dept") @Validated Dept dept,
                              BindingResult bindingResult, ActionRequest req,
                              ActionResponse resp) throws IOException, WindowStateException {
        logger.info("Received dept bean from form: " + dept);

        if (bindingResult.hasErrors()) {
            logger.info("Validation failed. BindingResult: " + bindingResult);
            logger.info("Returning current page");
            resp.setRenderParameter("action", "/deptadd_form.html");
        } else {
            logger.info("Validation succesfull " + dept);
            deptService.addDept(dept);

            logger.info("Redirecting to depts.jsp");
            ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String) req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
            redirectURL.setParameter("action", "/deptlist.html");
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            resp.sendRedirect(redirectURL.toString());
            logger.info("Redirect link generated" + redirectURL.toString());
        }
    }

    //-------------------------------deptedit_form.html-----------------------------------------

    @RequestMapping(params = "action=/deptedit_form.html")
    public String renderDeptEditForm(@RequestParam("deptId") int id, Model model) {
        logger.info("Rendering dept_edit.jsp");

        //if attribute "dept" exist in model after actionDeptEditForm(there were some errors)
        //return it to page displaying errors, else return dept by id
        Dept dept;
        if (model.containsAttribute("dept")) {
            dept = (Dept) model.asMap().get("dept");
        } else {
            dept = deptService.getDeptById(id);
        }
        model.addAttribute(dept);

        return "dept_edit";
    }

    @RequestMapping(params = "action=/deptedit_form.html")
    public void actionDeptEditForm(@RequestParam("deptId") int id, ActionResponse resp) {
        resp.setRenderParameter("deptId", "" + id);
        resp.setRenderParameter("action", "/deptedit_form.html");
    }

    //-------------------------------deptedit.html-----------------------------------------

    @RequestMapping(params = "action=/deptedit.html")
    public void actionDeptEdit(@ModelAttribute("dept") @Validated Dept dept,
                               BindingResult bindingResult, ActionRequest req,
                               ActionResponse resp) throws IOException, WindowStateException {
        logger.info("Received dept bean from form: " + dept);

        if (bindingResult.hasErrors()) {
            logger.info("Validation failed. BindingResult: " + bindingResult);
            logger.info("Returning current page");
            resp.setRenderParameter("deptId", "" + dept.getId());
            resp.setRenderParameter("action", "/deptedit_form.html");
        } else {
            logger.info("Validation succesfull " + dept);
            deptService.updateDept(dept);

            logger.info("Redirecting to depts.jsp");
            ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            String portletName = (String) req.getAttribute(WebKeys.PORTLET_ID);
            PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(req),
                    portletName, themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
            redirectURL.setParameter("jspPage", "/WEB-INF/views/depts.jsp");
            redirectURL.setParameter("action", "/deptlist.html");
            redirectURL.setWindowState(WindowState.MAXIMIZED);
            resp.sendRedirect(redirectURL.toString());
            logger.info("Redirect link generated" + redirectURL.toString());
        }
    }

}
