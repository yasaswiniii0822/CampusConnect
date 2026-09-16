<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activePage" value="profile" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>${profileUser.name} — CampusConnect</title>
  <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="page-shell">
  <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

  <main class="container" style="max-width:880px; padding-top:28px; padding-bottom:70px;">

    <div class="card profile-header">
      <div class="profile-avatar" style="background:${profileUser.avatarColor};">${profileUser.initial}</div>
      <div class="profile-meta">
        <h2>${profileUser.name}</h2>
        <div class="sub">${profileUser.department} &middot; ${profileUser.year} &middot; ${profileUser.college}</div>
        <p class="mb-0">${empty profileUser.bio ? 'No bio yet.' : profileUser.bio}</p>
      </div>
    </div>

    <div class="profile-grid">
      <div class="card">
        <div class="section-label">Skills</div>
        <div class="skill-tags">
          <c:forEach var="skill" items="${profileUser.skills}">
            <span class="pill">${skill}</span>
          </c:forEach>
          <c:if test="${empty profileUser.skills}"><span style="color:var(--ink-faint); font-size:0.88rem;">No skills added yet.</span></c:if>
        </div>
      </div>

      <div class="card">
        <div class="section-label">Interests</div>
        <div class="skill-tags">
          <c:forEach var="interest" items="${profileUser.interests}">
            <span class="pill pill-green">${interest}</span>
          </c:forEach>
          <c:if test="${empty profileUser.interests}"><span style="color:var(--ink-faint); font-size:0.88rem;">No interests picked yet.</span></c:if>
        </div>
      </div>
    </div>

    <c:if test="${isOwnProfile}">
      <div class="card" style="margin-top:18px;">
        <div class="section-label">Edit your profile</div>
        <form action="${pageContext.request.contextPath}/updateProfile" method="post">
          <label for="bio">Bio</label>
          <textarea id="bio" name="bio" placeholder="AI/ML enthusiast interested in building products for real-world problems.">${profileUser.bio}</textarea>

          <div class="form-row">
            <div>
              <label for="department">Department</label>
              <input type="text" id="department" name="department" value="${profileUser.department}">
            </div>
            <div>
              <label for="year">Year</label>
              <select id="year" name="year">
                <option value="1st Year" ${profileUser.year == '1st Year' ? 'selected' : ''}>1st Year</option>
                <option value="2nd Year" ${profileUser.year == '2nd Year' ? 'selected' : ''}>2nd Year</option>
                <option value="3rd Year" ${profileUser.year == '3rd Year' ? 'selected' : ''}>3rd Year</option>
                <option value="4th Year" ${profileUser.year == '4th Year' ? 'selected' : ''}>4th Year</option>
              </select>
            </div>
          </div>

          <label for="newSkill">Add a skill</label>
          <input type="text" id="newSkill" name="newSkill" placeholder="e.g. Figma">

          <button type="submit" class="btn btn-accent">Save changes</button>
        </form>
      </div>
    </c:if>

  </main>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
