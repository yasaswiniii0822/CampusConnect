package com.campusconnect.servlet;

import com.campusconnect.dao.EventDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registerEvent")
public class RegisterEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        int eventId = Integer.parseInt(req.getParameter("eventId"));
        String action = req.getParameter("action"); // "register" or "unregister"

        try {
            if ("unregister".equals(action)) {
                eventDAO.unregister(eventId, userId);
            } else {
                eventDAO.register(eventId, userId);
            }
            resp.sendRedirect(req.getContextPath() + "/events");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
