<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activePage" value="discover" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Discover — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">
  <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

  <main class="container" style="max-width:1000px; padding-top:28px; padding-bottom:70px;">
    <h1 style="font-size:2rem;">Find people</h1>
    <p class="lead" style="max-width:100%; margin-bottom:26px;">Search by name, skill, or interest — great for finding teammates or just meeting people in your field.</p>

    <form action="${pageContext.request.contextPath}/discover" method="get" class="search-bar">
      <input type="text" name="q" placeholder="Try &quot;Python&quot;, &quot;UI/UX&quot;, or a name..." value="${query}">
      <select name="department">
        <option value="">All departments</option>
        <c:forEach var="dept" items="${departments}">
          <option value="${dept}" ${dept == selectedDepartment ? 'selected' : ''}>${dept}</option>
        </c:forEach>
      </select>
      <button type="submit" class="btn btn-accent">Search</button>
    </form>

    <c:choose>
      <c:when test="${empty results}">
        <div class="card empty-state">
          <span class="icon">🔍</span>
          No students matched that search yet. Try a broader skill or interest.
        </div>
      </c:when>
      <c:otherwise>
        <div class="people-grid">
          <c:forEach var="person" items="${results}">
            <div class="card person-card">
              <div class="profile-avatar" style="background:${person.avatarColor};">${person.initial}</div>
              <h3>${person.name}</h3>
              <div class="sub">${person.department} &middot; ${person.year}</div>
              <div class="skill-tags" style="margin-bottom:14px;">
                <c:forEach var="skill" items="${person.skills}" begin="0" end="2">
                  <span class="pill pill-muted">${skill}</span>
                </c:forEach>
              </div>
              <a href="${pageContext.request.contextPath}/profile?id=${person.id}" class="btn btn-outline btn-sm btn-block">View profile</a>
            </div>
          </c:forEach>
        </div>
      </c:otherwise>
    </c:choose>
  </main>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
