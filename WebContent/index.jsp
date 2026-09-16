<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>CampusConnect — Your campus, in one place</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">

  <nav class="navbar">
    <div class="navbar-inner">
      <a href="${pageContext.request.contextPath}/index.jsp" class="brand"><span class="dot"></span>CampusConnect</a>
      <div class="nav-links"></div>
      <div class="nav-right">
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-ghost btn-sm">Log in</a>
        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-accent btn-sm">Sign up free</a>
      </div>
    </div>
  </nav>

  <main class="container">
    <section class="hero">
      <span class="eyebrow">Built for one campus at a time</span>
      <h1>Everything happening on your campus, <span class="italic-accent">finally in one place.</span></h1>
      <p class="lead">Discover people, events, opportunities and communities that matter to you — instead of piecing your college life together from ten different apps.</p>
      <div class="hero-actions">
        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-accent">Get started</a>
        <a href="#how-it-works" class="btn btn-outline">See how it works</a>
      </div>

      <div class="quick-actions">
        <div class="quick-action">
          <span class="icon">🧭</span>
          <strong>Find People</strong>
          <span>Match by skills, interests and goals</span>
        </div>
        <div class="quick-action">
          <span class="icon">🎉</span>
          <strong>Explore Events</strong>
          <span>Workshops, fests, and club meetups</span>
        </div>
        <div class="quick-action">
          <span class="icon">💼</span>
          <strong>Find Opportunities</strong>
          <span>Internships, hackathons, scholarships</span>
        </div>
        <div class="quick-action">
          <span class="icon">🤝</span>
          <strong>Build a Team</strong>
          <span>Find collaborators for your next project</span>
        </div>
      </div>
    </section>

    <section id="how-it-works" class="feature-grid">
      <div class="feature-card">
        <span class="tag">Discover → Connect</span>
        <h3>Stop missing what matters</h3>
        <p>No more scrolling five WhatsApp groups and a notice board. Your feed surfaces what's relevant to your college, department, year and interests.</p>
      </div>
      <div class="feature-card">
        <span class="tag">Participate → Collaborate</span>
        <h3>Find your people</h3>
        <p>Search students by skill and interest, join communities that fit you, and get matched with teammates for your next hackathon or project.</p>
      </div>
      <div class="feature-card">
        <span class="tag">Grow</span>
        <h3>One profile, every achievement</h3>
        <p>Your CampusConnect profile doubles as a running record of what you've built, joined, organized, and won — a portfolio that grows with you.</p>
      </div>
    </section>

    <section class="footer-cta">
      <h2>Your campus is more interesting than ten separate apps.</h2>
      <p>Join CampusConnect with your college email and see what's actually happening around you.</p>
      <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-accent">Create your profile</a>
    </section>
  </main>
</div>
</body>
</html>
