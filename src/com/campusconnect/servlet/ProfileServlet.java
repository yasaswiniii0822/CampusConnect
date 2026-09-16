package com.campusconnect.servlet;

import com.campusconnect.dao.UserDAO;
import com.campusconnect.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer sessionUserId = (Integer) req.getSession().getAttribute("userId");

        // Support viewing someone else's profile via ?id=, default to your own
        int viewId = sessionUserId;
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try { viewId = Integer.parseInt(idParam); } catch (NumberFormatException ignored) { }
        }

        try {
            User profileUser = userDAO.getById(viewId);
            if (profileUser == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }
            req.setAttribute("profileUser", profileUser);
            req.setAttribute("isOwnProfile", viewId == sessionUserId);
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
