FROM tomcat:8.5.54-jdk8-corretto

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/javaschool.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh","run"]