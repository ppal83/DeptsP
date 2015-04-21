package com.aimprosoft.handlers.util;

import com.aimprosoft.model.Employee;

import javax.portlet.PortletRequest;
import java.sql.Date;

public class EmployeeFromReq {

    public static Employee create(PortletRequest req) {
        String name = req.getParameter("name");
        Date birthDate = Date.valueOf(req.getParameter("Birthday"));
        Date hireDate = Date.valueOf(req.getParameter("HireDate"));
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        int deptId = Integer.parseInt(req.getParameter("deptId"));
        int salary = Integer.parseInt( req.getParameter("salary") );

        return null; // new Employee(name, birthDate, hireDate, address, email, deptId, salary);
    }
}
