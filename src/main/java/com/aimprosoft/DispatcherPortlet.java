package com.aimprosoft;

import com.aimprosoft.DAO.DAOFactory;
import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.DAO.EmployeeDAO;
import com.aimprosoft.handlers.Handler;
import com.aimprosoft.handlers.dept.*;
import com.aimprosoft.handlers.employee.*;
import com.aimprosoft.model.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.portlet.*;

public class DispatcherPortlet extends GenericPortlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherPortlet.class);

    private static DAOFactory daoFactory = DAOFactory.getInstance();
    private static DeptDAO deptDAO = daoFactory.getDeptDAO();
    private static Map<String, Object> DAOs;

    private static final Map<String, Handler> renderHandlers = new HashMap<>();
    private static final Map<String, Handler> actionHandlers = new HashMap<>();

    @Override
    public void init() throws PortletException {

        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        DAOs = new HashMap<>(2);
        DAOs.put("deptDAO", deptDAO);
        DAOs.put("employeeDAO", employeeDAO);

        logger.info("Registering render handlers");

        renderHandlers.put("/emplist.html", new EmployeeListHandler());
        renderHandlers.put("/deptlist.html", new DeptListHandler());
        renderHandlers.put("/deptedit_form.html", new DeptEditFormHandler());
        renderHandlers.put("/empedit_form.html", new EmployeeEditFormHandler());
        renderHandlers.put("/deptadd_form.html", new DeptAddFormHandler());
        renderHandlers.put("/empadd_form.html", new EmployeeAddForm());

        logger.info("Registering action handlers");

        actionHandlers.put("/empdel.html", new EmployeeDeleteHandler());
        actionHandlers.put("/empadd.html", new EmployeeUpdateHandler());
        actionHandlers.put("/empedit.html", new EmployeeUpdateHandler());
        actionHandlers.put("/deptdel.html", new DeptDeleteHandler());
        actionHandlers.put("/deptadd.html", new DeptUpdateHandler());
        actionHandlers.put("/deptedit.html", new DeptUpdateHandler());

    }

    @Override
    protected void doView(RenderRequest req, RenderResponse resp)
            throws PortletException, IOException {

        String path = req.getParameter("action");

        if (path != null) {
            logger.info("Request received for rendering " + path);
            matchHandler(renderHandlers, req, resp, path);
        } else {        //initial view
            List<Dept> deptList = deptDAO.getAllDepts();
            req.setAttribute("deptsList", deptList);
            logger.info("Request received for rendering initial view index.jsp");
            this.getPortletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }

    @Override
    public void processAction(ActionRequest req, ActionResponse resp)
            throws PortletException, IOException {

        String path = req.getParameter("action");

        if (actionHandlers.keySet().contains(path)) {
            logger.info("Request received for redirect action " + path);
            matchHandler(actionHandlers, req, resp, path);
        } else {
            resp.setRenderParameters(req.getParameterMap());
        }
    }

    private void matchHandler(Map<String, Handler> handlers,
                              PortletRequest req, PortletResponse resp, String path)
            throws PortletException, IOException {

        logger.info("Matching appropriate handler");
        Handler handler = handlers.get(path);

        if (handler != null) {
            logger.info("Handler found: " + handler.getClass().getSimpleName());
            handler.handle(req, resp, DAOs, this.getPortletContext());
        } else {
            logger.info("Didn't found handler");
        }
    }
}