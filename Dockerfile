FROM tomcat:9.0-jdk25-temurin

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY CampusConnect.war /usr/local/tomcat/webapps/CampusConnect.war

EXPOSE 8080

CMD ["catalina.sh", "run"]