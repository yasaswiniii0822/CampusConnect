<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>What are you interested in? — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">
  <nav class="navbar">
    <div class="navbar-inner">
      <a href="${pageContext.request.contextPath}/index.jsp" class="brand"><span class="dot"></span>CampusConnect</a>
    </div>
  </nav>

  <main class="container" style="max-width:720px; padding-top:50px; padding-bottom:80px;">
    <span class="eyebrow">Step 2 of 2</span>
    <h1 style="font-size:2.1rem;">What are you interested in?</h1>
    <p class="lead" style="max-width:100%;">Pick as many as you like — this shapes your feed, your recommended people, and the events we surface for you. You can always change these later from your profile.</p>

    <form action="${pageContext.request.contextPath}/onboarding" method="post">
      <c:forEach var="entry" items="${groupedInterests}">
        <div class="interest-category card card-tight">
          <div class="section-label">${entry.key}</div>
          <div class="interest-chips">
            <c:forEach var="interest" items="${entry.value}">
              <span class="chip-check">
                <input type="checkbox" id="int-${interest.id}" name="interest" value="${interest.id}">
                <label for="int-${interest.id}">${interest.name}</label>
              </span>
            </c:forEach>
          </div>
        </div>
      </c:forEach>

      <div class="flex justify-between items-center" style="margin-top:26px;">
        <span id="interest-count" class="pill pill-muted">0 interests selected</span>
        <button type="submit" class="btn btn-accent">Take me to my feed</button>
      </div>
    </form>
  </main>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
