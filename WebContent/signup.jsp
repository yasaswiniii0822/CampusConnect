<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Sign up — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="auth-shell">

  <div class="auth-visual">
    <a href="${pageContext.request.contextPath}/index.jsp" class="brand" style="color:#faf3e7;"><span class="dot"></span>CampusConnect</a>
    <div>
      <h1>Discover → Connect → Participate → Collaborate → Grow.</h1>
      <p class="quote">"One profile that doubles as your portfolio, your event calendar, and your team finder."</p>
    </div>
  </div>

  <div class="auth-form-side">
    <div class="auth-card">
      <div class="brand"><span class="dot"></span>CampusConnect</div>
      <h2>Create your account</h2>
      <p>Use your college email so we can verify you as a campus member.</p>

      <c:if test="${not empty error}">
        <div class="field-error">${error}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/register" method="post">
        <label for="name">Full name</label>
        <input type="text" id="name" name="name" placeholder="Yasaswini Prathipati" required>

        <label for="email">College email</label>
        <input type="email" id="email" name="email" placeholder="you@college.edu" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="At least 8 characters" required minlength="8">

        <label for="college">College</label>
        <input type="text" id="college" name="college" placeholder="Vardhaman College of Engineering" required>

        <div class="form-row">
          <div>
            <label for="department">Department</label>
            <input type="text" id="department" name="department" placeholder="Computer Science">
          </div>
          <div>
            <label for="year">Year</label>
            <select id="year" name="year">
              <option value="1st Year">1st Year</option>
              <option value="2nd Year">2nd Year</option>
              <option value="3rd Year">3rd Year</option>
              <option value="4th Year">4th Year</option>
            </select>
          </div>
        </div>

        <button type="submit" class="btn btn-accent btn-block">Create account</button>
      </form>

      <div class="auth-switch">Already on CampusConnect? <a href="${pageContext.request.contextPath}/login.jsp">Log in</a></div>
    </div>
  </div>

</div>
</body>
</html>
