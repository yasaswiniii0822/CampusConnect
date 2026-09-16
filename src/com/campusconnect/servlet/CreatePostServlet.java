package com.campusconnect.servlet;

import com.campusconnect.dao.PostDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {

    private final PostDAO postDAO = new PostDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String content = req.getParameter("content");
        String postType = req.getParameter("postType");
        if (postType == null || postType.isEmpty()) postType = "general";

        if (content != null && !content.trim().isEmpty()) {
            try {
                postDAO.createPost(userId, postType, content.trim());
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/feed");
    }
}
