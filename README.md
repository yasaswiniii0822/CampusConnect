# CampusConnect — MVP build

A real, working slice of the CampusConnect PRD: signup/login (JDBC + sessions),
onboarding interest picker, home feed, student profiles, people discovery, and
events with registration. Built with plain **Java Servlets + JSP** (no
framework) on **Tomcat**, backed by **MySQL**.

This is intentionally *not* the whole 60-section PRD — it's the MVP slice you
asked for, with the screens you said matter most (landing, feed, auth/
onboarding, profile, discover, events) built to be visually real, not
placeholders.

---

## 1. What you need installed first

1. **JDK 11 or newer** — check with `java -version` in a terminal.
2. **Apache Tomcat 9** — download from https://tomcat.apache.org/download-90.cgi
   (get the "Core" zip/tar.gz for your OS, or use Eclipse's built-in server if
   you're using the Eclipse IDE — see step 4).
3. **MySQL 8** (MySQL Community Server) — download from
   https://dev.mysql.com/downloads/mysql/ or install via your OS's package
   manager. During install, set a root password and **remember it** — you'll
   need it in step 3.
4. **Eclipse IDE for Enterprise Java Developers** (recommended, easiest way to
   run a Servlet/JSP project) — download from https://www.eclipse.org/downloads/
   — OR be comfortable compiling and deploying a WAR by hand (instructions for
   both are below).

---

## 2. Project folder layout

```
CampusConnect/
├── src/                      → all .java source files (servlets, DAOs, models)
├── WebContent/               → everything Tomcat actually serves
│   ├── *.jsp                 → the pages (index, signup, login, feed, ...)
│   ├── css/style.css         → all styling
│   ├── js/app.js             → small UI interactions
│   └── WEB-INF/
│       ├── web.xml           → deployment descriptor
│       ├── lib/              → JAR dependencies go here (see step 3)
│       ├── classes/          → compiled .java files go here (Eclipse does this for you)
│       └── jspf/             → shared header/navbar fragments
├── sql/schema.sql            → run this once to create the database
└── README.md                 → this file
```

---

## 3. Set up the database

1. Open a terminal and log into MySQL:
   ```
   mysql -u root -p
   ```
   Enter the root password you set during install, then type `exit;` to leave
   the prompt once you're in (we'll run the file properly next).

2. Run the schema file, which creates the `campusconnect` database, all
   tables, and some starter data (interests, skills, and 3 demo events):
   ```
   mysql -u root -p < sql/schema.sql
   ```
   Enter your password when prompted. If it finishes with no errors, the
   database is ready.

3. Open `src/com/campusconnect/db/DBConnection.java` and change this line to
   your actual MySQL root password:
   ```java
   private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
   ```

---

## 4. Add the two required JAR files

The project needs two libraries that aren't bundled with Tomcat by default.
Download both and put them in `WebContent/WEB-INF/lib/`:

1. **MySQL Connector/J** (the JDBC driver) — download the "Platform
   Independent" ZIP/TAR from
   https://dev.mysql.com/downloads/connector/j/ and pull out the file named
   something like `mysql-connector-j-8.x.x.jar`.

2. **JSTL 1.2** (used for the `<c:forEach>` / `<c:if>` / `<fmt:formatDate>`
   tags in the JSPs) — you need two JARs: `jakarta.servlet.jsp.jstl-api.jar`
   and `jakarta.servlet.jsp.jstl.jar` (or the older `jstl-1.2.jar` +
   `standard.jar` combo, depending on what you can find — any JSTL 1.2-
   compatible pair that exposes the `http://java.sun.com/jsp/jstl/core`
   taglib URI will work). Search "JSTL 1.2 jar download" if the Maven
   Central links move.

After this step, `WebContent/WEB-INF/lib/` should have 2–3 JAR files in it.

---

## 5. Run it in Eclipse (recommended)

1. Open Eclipse → **File → Import → Dynamic Web Project** (or **General →
   Projects from Folder or Archive**, pointing at the `CampusConnect` folder).
   If Eclipse doesn't recognize it as a Dynamic Web Project automatically:
   - Create a new empty **Dynamic Web Project** named `CampusConnect`.
   - Copy the contents of this project's `src/` into the new project's `src/`.
   - Copy the contents of this project's `WebContent/` into the new project's
     `WebContent/` (or `src/main/webapp/` if Eclipse created a Maven-style
     layout — merge folders, don't overwrite `web.xml` if Eclipse generated
     its own; use ours).
2. Right-click the project → **Properties → Targeted Runtimes** → make sure
   **Apache Tomcat v9.0** is checked (add a new server runtime pointing at
   your Tomcat install if it's not listed).
3. Right-click the project → **Run As → Run on Server** → choose Tomcat 9 →
   Finish.
4. Eclipse will compile everything, deploy it, and open a browser to
   `http://localhost:8080/CampusConnect/`.

---

## 6. Run it by hand (no IDE)

1. Compile the Java source into `WEB-INF/classes`, telling `javac` where to
   find the servlet API (inside Tomcat's `lib` folder) and your new JARs:
   ```
   cd CampusConnect
   mkdir -p WebContent/WEB-INF/classes
   javac -cp "WebContent/WEB-INF/lib/*:/path/to/tomcat/lib/*" \
         -d WebContent/WEB-INF/classes \
         $(find src -name "*.java")
   ```
2. Package the `WebContent` folder as a WAR:
   ```
   cd WebContent
   jar -cvf ../CampusConnect.war *
   cd ..
   ```
3. Copy the WAR into Tomcat and start Tomcat:
   ```
   cp CampusConnect.war /path/to/tomcat/webapps/
   /path/to/tomcat/bin/startup.sh
   ```
4. Visit `http://localhost:8080/CampusConnect/`.

---

## 7. Try it out

1. Go to the landing page → **Sign up free**.
2. Register with any email (it doesn't have to be a real college domain for
   this build — that verification check is a V2 item, see below).
3. Pick some interests on the onboarding screen → land on your feed.
4. Post something, check out **Discover** (search "Python" — the seed data
   includes some skills, but you'll need at least one other registered user
   with that skill to see a result), register for one of the 3 seeded demo
   events, and check your **Profile**.

---

## 8. What's deliberately NOT built yet

This is the MVP slice, not the full 60-section PRD. Left out on purpose so the
core loop (auth → onboarding → feed → discover → events → profile) is real
and solid rather than everything being half-built:

- College-domain email verification (currently any email is accepted)
- Connections/messaging, communities, opportunities hub, admin dashboard
- AI recommendations/matching, gamification, resource library, notifications
- Password hashing here is salted SHA-256 (fine for a student project); swap
  in BCrypt if this ever needs to be production-grade

These map to the PRD's own V2/V3 lists (sections 54–55) — good next steps
once the MVP feels solid.
