package com.aimprosoft.handlers.util;

import com.aimprosoft.model.Dept;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

public class DeptFromReq {

    public static Dept create(PortletRequest req) {
        String name = req.getParameter("name");

        return new Dept(name);
    }
}
