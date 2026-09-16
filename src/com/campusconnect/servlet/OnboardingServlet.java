package com.campusconnect.servlet;

import com.campusconnect.dao.InterestDAO;
import com.campusconnect.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/onboarding")
public class OnboardingServlet extends HttpServlet {

    private final InterestDAO interestDAO = new InterestDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Map<String, List<InterestDAO.Interest>> grouped = interestDAO.getAllGroupedByCategory();
            req.setAttribute("groupedInterests", grouped);
            req.getRequestDispatcher("/onboarding.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String[] selected = req.getParameterValues("interest");

        List<Integer> ids = new ArrayList<>();
        if (selected != null) {
            for (String s : selected) ids.add(Integer.parseInt(s));
        }

        try {
            userDAO.saveInterests(userId, ids);
            userDAO.markOnboarded(userId);
            resp.sendRedirect(req.getContextPath() + "/feed");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
