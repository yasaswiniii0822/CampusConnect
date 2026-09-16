<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="activePage" value="feed" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Your feed — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">
  <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

  <div class="app-layout">

    <!-- Left column: quick nav / mini profile -->
    <aside class="side-col">
      <div class="card card-tight" style="margin-bottom:16px;">
        <div class="flex items-center gap-8" style="margin-bottom:10px;">
          <span class="avatar-bubble" style="background:${currentUser.avatarColor}; width:48px; height:48px; font-size:1.2rem;">${currentUser.initial}</span>
          <div>
            <strong>${currentUser.name}</strong><br>
            <span style="font-size:0.8rem; color:var(--ink-faint);">${currentUser.department} &middot; ${currentUser.year}</span>
          </div>
        </div>
        <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline btn-sm btn-block">View profile</a>
      </div>
      <div class="card card-tight">
        <div class="section-label">Jump to</div>
        <div style="display:flex; flex-direction:column; gap:2px;">
          <a href="${pageContext.request.contextPath}/discover" class="btn btn-ghost btn-sm" style="justify-content:flex-start;">🧭 Find people</a>
          <a href="${pageContext.request.contextPath}/events" class="btn btn-ghost btn-sm" style="justify-content:flex-start;">🎉 Browse events</a>
        </div>
      </div>
    </aside>

    <!-- Main column: composer + feed -->
    <main>
      <div class="card composer" style="margin-bottom:20px;">
        <form action="${pageContext.request.contextPath}/createPost" method="post">
          <input type="hidden" name="postType" value="general">
          <textarea name="content" placeholder="Share something with your campus — a question, an update, a project idea..." required></textarea>
          <div class="composer-actions">
            <div class="post-type-select">
              <span class="pill pill-outline pill-active" data-post-type="general">General</span>
              <span class="pill pill-outline" data-post-type="question">Question</span>
              <span class="pill pill-outline" data-post-type="achievement">Achievement</span>
              <span class="pill pill-outline" data-post-type="project">Project</span>
            </div>
            <button type="submit" class="btn btn-accent composer-submit">Post</button>
          </div>
        </form>
      </div>

      <div class="section-label">For you</div>

      <c:choose>
        <c:when test="${empty posts}">
          <div class="card empty-state">
            <span class="icon">📭</span>
            Nothing in the feed yet — be the first to post something.
          </div>
        </c:when>
        <c:otherwise>
          <c:forEach var="post" items="${posts}">
            <div class="card post-card">
              <div class="post-header">
                <span class="avatar-bubble" style="background:${post.authorColor};">${post.authorInitial}</span>
                <div class="meta">
                  <strong>${post.authorName}</strong>
                  <span><fmt:formatDate value="${post.createdAt}" pattern="MMM d, h:mm a"/> &middot; ${post.postType}</span>
                </div>
              </div>
              <div class="post-body">${post.content}</div>
              <div class="post-actions">
                <button type="button">👍 Like</button>
                <button type="button">💬 Comment</button>
                <button type="button">↗ Share</button>
              </div>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </main>

    <!-- Right column: recommended events -->
    <aside class="side-col">
      <div class="card">
        <div class="section-label">Upcoming events</div>
        <c:choose>
          <c:when test="${empty upcomingEvents}">
            <p style="font-size:0.85rem;">No events on the calendar yet.</p>
          </c:when>
          <c:otherwise>
            <c:forEach var="ev" items="${upcomingEvents}">
              <div class="mini-event">
                <strong>${ev.title}</strong>
                <span><fmt:formatDate value="${ev.eventDate}" pattern="MMM d"/> &middot; ${ev.organizer}</span>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/events" class="btn btn-ghost btn-sm btn-block" style="margin-top:10px;">See all events</a>
      </div>
    </aside>

  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
