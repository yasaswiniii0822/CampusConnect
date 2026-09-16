package com.campusconnect.servlet;

import com.campusconnect.dao.EventDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/createEvent")
public class CreateEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String organizer = req.getParameter("organizer");
        String category = req.getParameter("category");
        String dateStr = req.getParameter("eventDate");
        String time = req.getParameter("eventTime");
        String venue = req.getParameter("venue");
        int capacity = parseIntSafe(req.getParameter("capacity"), 0);

        try {
            Date eventDate = (dateStr != null && !dateStr.isEmpty()) ? Date.valueOf(dateStr) : null;
            eventDAO.createEvent(title, description, organizer, category, eventDate, time, venue, capacity, userId);
            resp.sendRedirect(req.getContextPath() + "/events");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private int parseIntSafe(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (Exception e) { return fallback; }
    }
}
