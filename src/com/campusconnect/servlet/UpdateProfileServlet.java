package com.campusconnect.servlet;

import com.campusconnect.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String bio = req.getParameter("bio");
        String department = req.getParameter("department");
        String year = req.getParameter("year");
        String newSkill = req.getParameter("newSkill");

        try {
            userDAO.updateProfile(userId, bio, department, year);
            if (newSkill != null && !newSkill.trim().isEmpty()) {
                userDAO.addSkill(userId, newSkill.trim());
            }
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
