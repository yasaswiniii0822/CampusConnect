package com.campusconnect.servlet;

import com.campusconnect.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/discover")
public class DiscoverServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String query = req.getParameter("q");
        String department = req.getParameter("department");

        try {
            req.setAttribute("results", userDAO.search(query, department));
            req.setAttribute("departments", userDAO.getAllDepartments());
            req.setAttribute("query", query);
            req.setAttribute("selectedDepartment", department);
            req.getRequestDispatcher("/discover.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
