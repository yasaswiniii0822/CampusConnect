package com.campusconnect.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Any request under one of the protected paths must have a logged-in
 * session (session attribute "userId" set by LoginServlet), otherwise
 * it is redirected to the login page.
 */
@WebFilter(urlPatterns = {
        "/feed", "/profile", "/discover", "/events", "/onboarding",
        "/createPost", "/createEvent", "/registerEvent", "/updateProfile"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        if (loggedIn) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?next=" + request.getServletPath());
        }
    }

    @Override
    public void destroy() { }
}
