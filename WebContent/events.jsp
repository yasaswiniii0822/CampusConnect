<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="activePage" value="events" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Events — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">
  <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

  <main class="container" style="max-width:1000px; padding-top:28px; padding-bottom:70px;">
    <div class="flex justify-between items-center" style="margin-bottom:6px;">
      <h1 style="font-size:2rem; margin-bottom:0;">Campus events</h1>
      <button type="button" class="btn btn-accent btn-sm" onclick="document.getElementById('create-event-form').classList.toggle('hidden-form')">+ Create event</button>
    </div>
    <p class="lead" style="max-width:100%; margin-bottom:20px;">Workshops, fests, hackathons, and club meetups — register in one tap.</p>

    <div id="create-event-form" class="card hidden-form" style="margin-bottom:26px;">
      <div class="section-label">New event</div>
      <form action="${pageContext.request.contextPath}/createEvent" method="post">
        <label for="title">Title</label>
        <input type="text" id="title" name="title" placeholder="AI/ML Workshop" required>

        <label for="description">Description</label>
        <textarea id="description" name="description" placeholder="What's this event about?"></textarea>

        <div class="form-row">
          <div>
            <label for="organizer">Organizer</label>
            <input type="text" id="organizer" name="organizer" placeholder="AI Club">
          </div>
          <div>
            <label for="category">Category</label>
            <select id="category" name="category">
              <option>Technical</option>
              <option>Cultural</option>
              <option>Sports</option>
              <option>Workshop</option>
              <option>Networking</option>
              <option>Academic</option>
            </select>
          </div>
        </div>

        <div class="form-row">
          <div>
            <label for="eventDate">Date</label>
            <input type="date" id="eventDate" name="eventDate">
          </div>
          <div>
            <label for="eventTime">Time</label>
            <input type="text" id="eventTime" name="eventTime" placeholder="2:00 PM">
          </div>
        </div>

        <div class="form-row">
          <div>
            <label for="venue">Venue</label>
            <input type="text" id="venue" name="venue" placeholder="Engineering Block">
          </div>
          <div>
            <label for="capacity">Capacity</label>
            <input type="number" id="capacity" name="capacity" placeholder="100">
          </div>
        </div>

        <button type="submit" class="btn btn-accent">Publish event</button>
      </form>
    </div>

    <c:choose>
      <c:when test="${empty events}">
        <div class="card empty-state">
          <span class="icon">🎪</span>
          No events on the calendar yet. Be the first to create one.
        </div>
      </c:when>
      <c:otherwise>
        <div class="events-grid">
          <c:forEach var="ev" items="${events}">
            <div class="card event-card">
              <span class="date-chip"><fmt:formatDate value="${ev.eventDate}" pattern="MMM d"/></span>
              <h3>${ev.title}</h3>
              <div class="event-meta">
                <div>🏷 ${ev.category} &middot; hosted by ${ev.organizer}</div>
                <div>📍 ${ev.venue} &middot; ${ev.eventTime}</div>
                <div>👥 ${ev.registeredCount}<c:if test="${ev.capacity > 0}"> / ${ev.capacity}</c:if> registered</div>
              </div>
              <div class="event-footer">
                <span class="pill pill-outline">${ev.category}</span>
                <form action="${pageContext.request.contextPath}/registerEvent" method="post">
                  <input type="hidden" name="eventId" value="${ev.id}">
                  <c:choose>
                    <c:when test="${ev.currentUserRegistered}">
                      <input type="hidden" name="action" value="unregister">
                      <button type="submit" class="btn btn-outline btn-sm">Registered ✓</button>
                    </c:when>
                    <c:otherwise>
                      <input type="hidden" name="action" value="register">
                      <button type="submit" class="btn btn-accent btn-sm">Register</button>
                    </c:otherwise>
                  </c:choose>
                </form>
              </div>
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
