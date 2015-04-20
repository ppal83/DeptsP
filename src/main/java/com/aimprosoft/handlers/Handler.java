package com.aimprosoft.handlers;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.io.IOException;
import java.util.Map;

public interface Handler {

    void handle(PortletRequest req, PortletResponse resp,
                Map<String, Object> DAOs, PortletContext pc) throws PortletException, IOException;

}
