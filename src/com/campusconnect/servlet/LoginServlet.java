package com.campusconnect.servlet;

import com.campusconnect.dao.UserDAO;
import com.campusconnect.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userDAO.authenticate(email, password);
            if (user == null) {
                req.setAttribute("error", "Incorrect email or password.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("avatarColor", user.getAvatarColor());

            if (!user.isOnboarded()) {
                resp.sendRedirect(req.getContextPath() + "/onboarding");
            } else {
                String next = req.getParameter("next");
                resp.sendRedirect(req.getContextPath() + (next != null && !next.isEmpty() ? next : "/feed"));
            }

        } catch (SQLException e) {
            req.setAttribute("error", "Something went wrong logging you in: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
