package com.campusconnect.servlet;

import com.campusconnect.dao.EventDAO;
import com.campusconnect.dao.PostDAO;
import com.campusconnect.dao.UserDAO;
import com.campusconnect.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/feed")
public class FeedServlet extends HttpServlet {

    private final PostDAO postDAO = new PostDAO();
    private final EventDAO eventDAO = new EventDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        try {
            User currentUser = userDAO.getById(userId);
            req.setAttribute("currentUser", currentUser);
            req.setAttribute("posts", postDAO.getFeed(20));
            // Show up to 3 upcoming events as "recommended" cards in the feed sidebar
            req.setAttribute("upcomingEvents", eventDAO.getUpcoming(userId).stream().limit(3).toArray());
            req.getRequestDispatcher("/feed.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
