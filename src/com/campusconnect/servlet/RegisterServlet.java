package com.campusconnect.servlet;

import com.campusconnect.dao.UserDAO;
import com.campusconnect.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String college = req.getParameter("college");
        String department = req.getParameter("department");
        String year = req.getParameter("year");

        if (isBlank(name) || isBlank(email) || isBlank(password) || isBlank(college)) {
            req.setAttribute("error", "Please fill in your name, college email, password, and college.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            return;
        }

        try {
            int userId = userDAO.createUser(name, email, password, college, department, year);
            if (userId == -1) {
                req.setAttribute("error", "That email is already registered. Try logging in instead.");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            User created = userDAO.getById(userId);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", userId);
            session.setAttribute("userName", name);
            session.setAttribute("avatarColor", created != null ? created.getAvatarColor() : "#c1502f");

            resp.sendRedirect(req.getContextPath() + "/onboarding");

        } catch (SQLException e) {
            req.setAttribute("error", "Something went wrong creating your account: " + e.getMessage());
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
