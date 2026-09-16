<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Log in — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="auth-shell">

  <div class="auth-visual">
    <a href="${pageContext.request.contextPath}/index.jsp" class="brand" style="color:#faf3e7;"><span class="dot"></span>CampusConnect</a>
    <div>
      <h1>Welcome back. Your campus has been busy.</h1>
      <p class="quote">"Meaningful campus connections, one login away."</p>
    </div>
  </div>

  <div class="auth-form-side">
    <div class="auth-card">
      <div class="brand"><span class="dot"></span>CampusConnect</div>
      <h2>Log in</h2>
      <p>Welcome back — pick up where you left off.</p>

      <c:if test="${not empty error}">
        <div class="field-error">${error}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="next" value="${param.next}">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="you@college.edu" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Your password" required>

        <button type="submit" class="btn btn-accent btn-block">Log in</button>
      </form>

      <div class="auth-switch">New to CampusConnect? <a href="${pageContext.request.contextPath}/signup.jsp">Create an account</a></div>
    </div>
  </div>

</div>
</body>
</html>
